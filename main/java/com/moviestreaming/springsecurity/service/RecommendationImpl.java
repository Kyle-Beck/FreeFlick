package com.moviestreaming.springsecurity.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviestreaming.springsecurity.container.MovieBatch;
import com.moviestreaming.springsecurity.dto.MoviePreview;
import com.moviestreaming.springsecurity.dto.Rating;
import com.moviestreaming.springsecurity.dto.TopTag;
import com.moviestreaming.springsecurity.enums.RatingType;
import com.moviestreaming.springsecurity.enums.TagType;
import com.moviestreaming.springsecurity.repository.MoviePreviewDao;
import com.moviestreaming.springsecurity.repository.RatingDao;
import com.moviestreaming.springsecurity.repository.TopTagDao;

@Service
public class RecommendationImpl implements Recommendation {
	@Autowired
	MoviePreviewDao moviePreviewDao;
	@Autowired
	TopTagDao topTagDao;
	@Autowired
	RatingDao ratingDao;

	private static final int maxGenreCategories = 5;
	private static final String[] defaultGenreCategories = {"Comedy", "Adventure", "Romance" };

	public RecommendationImpl() {
	}

	@Override
	public List<MovieBatch> getHomePreviews(String username) {
		
		// set of ids that is passed to findUnseenByTag() to prevent duplicate moviePreviews
		HashSet<Integer> ids = new HashSet<>();
		// list of batches to be shown on the home page
		List<MovieBatch> resultBatches = new ArrayList<MovieBatch>();

		// getRecommendations() returns null if not enough recommendations are found
		MovieBatch recommendedForYou = this.getRecommendations(username);
		if (recommendedForYou != null) {
			resultBatches.add(recommendedForYou);
			addIDsToSet(recommendedForYou, ids);
		}

		// get most popular movies and add to result
		MovieBatch popular = new MovieBatch("Popular", moviePreviewDao.findPopular());
		resultBatches.add(popular);
		addIDsToSet(popular, ids);	

		// get top user genres. Query movies for each and add to result if there are enough
		List<TopTag> userGenres = topTagDao.findTopTags(username, TagType.genre, maxGenreCategories);
		int i = 0;
		for (TopTag genreTag : userGenres) {
			List<MoviePreview> genreCategory = moviePreviewDao.findUnseenByTag(TagType.genre, genreTag.getName(),
					MovieBatch.maxTotalSize, username, ids);
			if (genreCategory.size() >= MovieBatch.groupSize) {
				MovieBatch genreBatch = new MovieBatch(genreTag.getName(), genreCategory);
				resultBatches.add(genreBatch);
				addIDsToSet(genreBatch, ids);
				i++;
			}
			if (i >= maxGenreCategories) {
				break;
			}
		}

		// if none of the top genre queries have enough results (ex: first time user), use default categories
		if (i == 0) {
			for (String genre : defaultGenreCategories) {
				List<MoviePreview> genreCategory = moviePreviewDao.findUnseenByTag(TagType.genre, genre,
						MovieBatch.maxTotalSize, username, ids);
				MovieBatch genreBatch = new MovieBatch(genre, genreCategory);
				resultBatches.add(genreBatch);
				addIDsToSet(genreBatch, ids);
			}
		}

		return resultBatches;
	}

	// This method populates the recommendation category on the home page
	@Override
	public MovieBatch getRecommendations(String username) {
		HashMap<Integer, Rating> map = new HashMap<>();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("CENSORED FOR SECURITY");

			// get ratings using different strategies and merge results into a map
			mergeRatings(map, ratingDao.findRatings(username, RatingType.genre, conn));
			mergeRatings(map, ratingDao.findRatings(username, RatingType.collaborative, conn));
			mergeRatings(map, ratingDao.findRatings(username, RatingType.actor, conn));
			mergeRatings(map, ratingDao.findRatings(username, RatingType.director, conn));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Statement has already been closed in the findRatings method
				// so we only need to close the connection here
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqlee) {
				sqlee.printStackTrace();
			}
		}

		// if there aren't enough recommendations, getSortedPreviewsFromMap() returns null
		List<MoviePreview> result = this.getSortedPreviewsFromMap(map);
		if (result == null) {
			// return null, because getHomePreviews() checks for null
			return null;
		}
		
		// return recommendations as a movie batch
		return new MovieBatch("Recommended For You", result);
	}

	@Override
	public MovieBatch getWatchRecommendations(String username, String movieID) {
		
		HashMap<Integer, Rating> map = new HashMap<>();
		Connection conn = null;

		try {
			conn = DriverManager.getConnection("CENSORED FOR SECURITY");
			
			// get ratings using different strategies and merge results into a map
			mergeRatings(map, ratingDao.findRatings(username, movieID, RatingType.collaborative, conn));
			mergeRatings(map, ratingDao.findRatings(username, movieID, RatingType.genre, conn));
			mergeRatings(map, ratingDao.findRatings(username, movieID, RatingType.actor, conn));
			mergeRatings(map, ratingDao.findRatings(username, movieID, RatingType.director, conn));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Statement has already been closed in the findRatings method
				// so we only need to close the connection here
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqlee) {
				sqlee.printStackTrace();
			}
		}

		
		List<MoviePreview> result = this.getSortedPreviewsFromMap(map);

		return new MovieBatch("Similar Titles", result);
	}

	// combines different recommendation strategies
	private static void mergeRatings(HashMap<Integer, Rating> map, List<Rating> ratings) {
		for (Rating r : ratings) {
			// If a rating for this movie doesn't already exist in the map: put this rating.
			// Else: add this rating score to existing rating score
			Rating existingRating = map.get(r.getMovieID());
			if (existingRating == null) {
				map.put(r.getMovieID(), r);
			} else {
				existingRating.setWeight(existingRating.getWeight() + r.getWeight());
			}
		}
	}
	
	// get previews from a list of recommendations
	private List<MoviePreview> getSortedPreviewsFromMap(HashMap<Integer, Rating> map) {
		// if we don't have enough ratings for 1 group, return null
		if (map.size() < MovieBatch.groupSize) {
			return null;
		}
		// add map Ratings into a list, so they can be sorted
		List<Rating> ratings = new ArrayList<>();
		for (int id : map.keySet()) {
			ratings.add(map.get(id));
		}
		Collections.sort(ratings);

		// Get a sublist of the best ratings
		// The sublist length should be the idealRecommendationSize (a multiple of
		// recommendationGroupSize)
		// If there aren't enough ratings for the Movie Batch MaxTotalSize,
		// then the length should be the largest possible multiple of groupSize
		int subListSize = Math.min(MovieBatch.maxTotalSize, ratings.size() - ratings.size() % MovieBatch.groupSize);
		ratings = ratings.subList(0, subListSize);

		// get previews for each remaining rating.
		List<MoviePreview> result = moviePreviewDao.findByRating(ratings);

		// the sorting is lost when we retrieve previews from the database
		// set the scores and resort them
		// TODO: Think of a way around this
		for (MoviePreview m : result) {
			m.setScore(map.get(m.getMovieID()).getWeight());
		}
		Collections.sort(result);

		return result;
	}
	
	private static void addIDsToSet(MovieBatch batch, HashSet<Integer> set) {
		for (MoviePreview mp : batch.movies) {
			set.add(mp.getMovieID());
		}
	}
}