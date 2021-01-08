package com.moviestreaming.springsecurity.service;

import java.util.List;

import com.moviestreaming.springsecurity.container.MovieBatch;

public interface Recommendation {
	public  List<MovieBatch> getHomePreviews(String username);
	public MovieBatch getWatchRecommendations(String username, String movieID);
	public MovieBatch getRecommendations(String username);
}
