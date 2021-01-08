package com.moviestreaming.springsecurity.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.moviestreaming.springsecurity.dto.Rating;
import com.moviestreaming.springsecurity.enums.RatingType;

public interface RatingDao {
	// Recommend a list of ratings given a user and a ratingType
	public List<Rating> findRatings(String username, RatingType ratingType, Connection conn) throws SQLException;
	// Recommend a list of ratings given a movie, a user, and a ratingType
	public List<Rating> findRatings(String username, String movieID, RatingType ratingType, Connection conn) throws SQLException;

}
