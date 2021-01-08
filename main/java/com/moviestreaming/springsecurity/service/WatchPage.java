package com.moviestreaming.springsecurity.service;

import com.moviestreaming.springsecurity.entity.MovieDetails;

// get movieDetails for watch page

public interface WatchPage {
	public MovieDetails findById(int id);
}
