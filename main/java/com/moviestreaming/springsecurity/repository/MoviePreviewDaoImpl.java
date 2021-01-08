package com.moviestreaming.springsecurity.repository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moviestreaming.springsecurity.dto.MoviePreview;
import com.moviestreaming.springsecurity.dto.Rating;
import com.moviestreaming.springsecurity.enums.TagType;

//TODO: popular and collaborative could be moved into ratingDao
@Repository
public class MoviePreviewDaoImpl implements MoviePreviewDao {
	@Autowired
	private EntityManager session;

	
	// This method is called by the RecommendationService 
	@Transactional
	@Override
	public List<MoviePreview> findByRating(List<Rating> ratings) {

		Iterator<Rating> it = ratings.iterator();
		if (!it.hasNext()) {
			return null;
		}

		// query String is csv list of movieIDs that can be used in a sql query
		String queryString = "";
		queryString += it.next().getMovieID();
		while (it.hasNext()) {
			queryString += ", " + it.next().getMovieID();
		}
		
		// the user doesn't have access to this method, so we don't need a prepared statement.
		Query query = session.createNativeQuery(
				"select movie_details.movieID as movieID, movie_details.title as title, movie_details.year as year, movie_details.poster_url as posterUrl from\r\n"
						+ "movie_details WHERE movieID IN (" + queryString + ")");
		return getPreviews(query);
	}

	@Transactional
	@Override
	public List<MoviePreview> findPopular() {

		Query query = session.createNativeQuery(
				"select movie_details.movieID as movieID, movie_details.title as title, movie_details.year as year, movie_details.poster_url as posterUrl from\r\n"
						+ "	(select movieID, count(*) from history\r\n" + "	where liked = 1\r\n"
						+ "	group by movieID\r\n" + "	order by count(*) desc\r\n" + "	limit 24) ids\r\n" + "join\r\n"
						+ "	movie_details\r\n" + "on movie_details.movieID = ids.movieID;");
		List<MoviePreview> result = getPreviews(query);
		return result;
	}

	@Transactional
	@Override
	public List<MoviePreview> findUnseenByTag(TagType tagType, String tagName, int limit, String username, HashSet<Integer> ids) {

		//TODO: use string builder
		Iterator<Integer> it = ids.iterator();
		String queryString = "";
		if(it.hasNext()) {
			queryString += it.next();
		}
		while (it.hasNext()) {
			queryString += ", " + it.next();
		}
		
		Query query = session.createNativeQuery(
				"select movie_details.movieID as movieID, movie_details.title as title, movie_details.year as year, movie_details.poster_url as posterUrl from\r\n"
						+ "	(select movieID from tags\r\n" + "	where type= ? AND name = ? AND movieID not in\r\n"
						+ "		(select movieID from history where username = ?) And movieID not in(" + queryString + ")) ids\r\n" + "join\r\n"
						+ "	movie_details\r\n" + "on ids.movieID = movie_details.movieID\r\n" + "limit ?;")
				.setParameter(1, tagType.toString()).setParameter(2, tagName).setParameter(3, username)
				.setParameter(4, limit);

		return getPreviews(query);
	}

	@Transactional
	@Override
	public List<MoviePreview> findSearchResults(String searchString) {
		String search = searchString.trim().replace('+', ' ');
		Query query = session.createNativeQuery(
				"select distinct movie_details.movieID as movieID, movie_details.title as title, movie_details.year as year, movie_details.poster_url as posterUrl from\r\n"
						+ "	(select distinct movieID, metaOrder, matchScore from\r\n"
						+ "		(select movieID, 1 as metaOrder, case when `title` = ? then 1 when `title` like ? then 2 else 3 end as matchScore\r\n"
						+ "		from movie_details\r\n" + "		where `title` like ? \r\n" + "		union\r\n"
						+ "		select movieID, 2 as metaOrder, case when `name` = ? then 1 else 2 end as matchScore from tags\r\n"
						+ "		where `name` like ?) matchedIDs\r\n" + "	) results\r\n" + "join\r\n"
						+ "	movie_details\r\n" + "on movie_details.movieID = results.movieID\r\n"
						+ "order by metaOrder, matchScore")
				.setParameter(1, search).setParameter(2, search + "%").setParameter(3, "%" + search + "%")
				.setParameter(4, search).setParameter(5, "%" + search + "%");
		return getPreviews(query);
	}

	@Override
	@Transactional
	public List<MoviePreview> findFilteredSearchResults(String search, int startYear, int endYear, String genre) {
		// search = search.trim().replace('+', ' ');
		Query query = session.createNativeQuery(
				"select distinct details.movieID as movieID, details.title as title, details.year as year, details.poster_url as posterUrl from\r\n"
						+ "	(select distinct matchedIDs.movieID, metaOrder, matchScore from\r\n"
						+ "		(select movieID, 1 as metaOrder, case when `title` = ? then 1 when `title` like ? then 2 else 3 end as matchScore\r\n"
						+ "		from movie_details \r\n" + "		where `title` like ? \r\n" + "			union\r\n"
						+ "		select movieID, 2 as metaOrder, case when `name` = ? then 1 when `name` like ? then 2 else 3 end as matchScore \r\n"
						+ "		from tags \r\n" + "		where `name` like ?) matchedIDs \r\n" + "	left join\r\n"
						+ "		(select movieID from tags where type = 'genre' and name like ?) filteredIDs\r\n"
						+ "	on matchedIDs.movieID = filteredIDs.movieID\r\n"
						+ "    where filteredIDs.movieID is not null) IDs\r\n" + "join\r\n"
						+ "	(select movieID, title, year, poster_url \r\n" + "    from movie_details\r\n"
						+ "    where year >= ? AND year <= ?) details\r\n" + "on details.movieID = IDs.movieID\r\n"
						+ "order by metaOrder, matchScore;")
				.setParameter(1, search).setParameter(2, search + "%").setParameter(3, "%" + search + "%")
				.setParameter(4, search).setParameter(5, search + "%").setParameter(6, "%" + search + "%")
				.setParameter(7, "%" + genre + "%").setParameter(8, startYear).setParameter(9, endYear);
		return getPreviews(query);
	}

	@Override
	public List<MoviePreview> findLikedHistory(String username) {
		Query query = session.createNativeQuery(
				"select movie_details.movieID as movieID, movie_details.title as title, movie_details.year as year, movie_details.poster_url as posterUrl from\r\n"
						+ "	(select movieID from history\r\n" + "	where username = ? AND liked = 1) likedHistory\r\n"
						+ "join\r\n" + "	movie_details\r\n" + "on likedHistory.movieID = movie_details.movieID;")
				.setParameter(1, username);
		return getPreviews(query);
	}

	@SuppressWarnings("deprecation")
	private List<MoviePreview> getPreviews(Query nativeQuery) {
		nativeQuery.unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(MoviePreview.class)).list();
		List<MoviePreview> result = nativeQuery.getResultList();
		return result;
	}
}
