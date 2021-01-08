package com.moviestreaming.springsecurity.repository;

// This DAO is used to get the movieID for the /watch page

public interface RandomMovieDAO {
	public int getRandomID(String username);
}
