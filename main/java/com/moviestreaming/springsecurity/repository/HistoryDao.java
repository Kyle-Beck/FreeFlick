package com.moviestreaming.springsecurity.repository;

// DAO for saving user history when user clicks "Like" or "Dislike"

public interface HistoryDao {
	public void saveOrUpdate(int movieId, String username, boolean liked);
	public int getRating(String movieID, String username);
}