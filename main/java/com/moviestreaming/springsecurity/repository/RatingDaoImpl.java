package com.moviestreaming.springsecurity.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moviestreaming.springsecurity.dto.Rating;
import com.moviestreaming.springsecurity.enums.RatingType;

@Repository
public class RatingDaoImpl implements RatingDao {

	@Override
	public List<Rating> findRatings(String username, RatingType ratingType, Connection conn) throws SQLException{

		String query;
		CallableStatement stmt = null;
		ResultSet rs;
		List<Rating> resultList = new ArrayList<Rating>();
		
		try {

			switch (ratingType) {
			case popular:
				query = "select movieID as movieID, count(*) as weight from history \r\n" + "where liked = 1 \r\n"
						+ "group by movieID \r\n" + "order by count(*) desc \r\n" + "limit 50";

				stmt = conn.prepareCall(query);
				rs = stmt.executeQuery(query);
				addRatingsToResult(resultList, rs);

				break;

			case collaborative:
				query = "{CALL collaborativeRecommendation(?)}";

				stmt = conn.prepareCall(query);
				stmt.setString(1, username);
				rs = stmt.executeQuery();
				addRatingsToResult(resultList, rs);

				break;

			default:
				query = "{CALL tagRecommendation(?, ?)}";
				
				stmt = conn.prepareCall(query);
				stmt.setString(1, username);
				stmt.setString(2, ratingType.toString());
				rs = stmt.executeQuery();
				addRatingsToResult(resultList, rs);
				
				break;
			}
			
			return resultList;
			
		} finally {
			try {
				// The connection should be closed where the exception is thrown
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException sqlee) {
				sqlee.printStackTrace();
			}
		}
	}
	
	@Override
	public List<Rating> findRatings(String username, String movieID, RatingType ratingType, Connection conn) throws SQLException{

		String query;
		CallableStatement stmt = null;
		ResultSet rs;
		List<Rating> resultList = new ArrayList<Rating>();
		
		try {

			switch (ratingType) {

			case collaborative:
				query = "{CALL collaborativeRecommendationByID(?, ?)}";

				stmt = conn.prepareCall(query);
				stmt.setString(1, username);
				stmt.setString(2, movieID);
				rs = stmt.executeQuery();
				addRatingsToResult(resultList, rs);

				break;

			default:
				query = "{CALL tagRecommendationByID(?, ?, ?)}";
				
				stmt = conn.prepareCall(query);
				stmt.setString(1, username);
				stmt.setString(2, movieID);
				stmt.setString(3, ratingType.toString());
				rs = stmt.executeQuery();
				addRatingsToResult(resultList, rs);
				
				break;
			}
			
			return resultList;
			
		} finally {
			try {
				// The connection should be closed where the exception is thrown
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException sqlee) {
				sqlee.printStackTrace();
			}
		}
	}
	
	// Convenience method for findRatings
	private void addRatingsToResult(List<Rating> resultList, ResultSet rs) throws SQLException {
		while (rs.next()) {
			Rating r = new Rating();
			r.setMovieID(rs.getInt(1));
			r.setWeight(rs.getDouble(2));
			resultList.add(r);
		}
	}
	
	@SuppressWarnings("deprecation")
	private List<Rating> getPreviews(Query nativeQuery) {

		nativeQuery.unwrap(NativeQuery.class).addScalar("weight", DoubleType.INSTANCE)
				.addScalar("movieID", IntegerType.INSTANCE)
				// TODO warning to remove with Hibernate 6 for setResultTransformer()
				.setResultTransformer(Transformers.aliasToBean(Rating.class)).list();
		List<Rating> result = nativeQuery.getResultList();
		return result;
	}

}
