package com.moviestreaming.springsecurity.repository;

import com.moviestreaming.springsecurity.entity.MovieDetails;

// Method that fetches movie embed code and movie details for /watch

public interface MovieDetailsDao {
	public MovieDetails findById(int id);
}
