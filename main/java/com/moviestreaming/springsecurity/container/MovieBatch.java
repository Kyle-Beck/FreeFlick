package com.moviestreaming.springsecurity.container;

import java.util.List;

import com.moviestreaming.springsecurity.dto.MoviePreview;

public class MovieBatch {
	// text that is displayed above the Bootstrap Carousel
	public String title;
	public List<MoviePreview> movies;

	// These variables are also used in the RecommendationService Class
	public static final int groupSize = 8;
	public static final int maxTotalSize = 24;

	public MovieBatch(String title, List<MoviePreview> movies) {
		// a null value can be passed on the watch history page if there is a problem with the db connection
		// in which case we leave both attributes null and check for a null title on the watch jsp
		if (movies != null) {
			this.title = title;
			// we want the list to be a multiple of 8 (maximum 24) so that bootstrap carousel looks nice.
			this.movies = movies.subList(0, Math.min(movies.size() - (movies.size() % groupSize), maxTotalSize));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MoviePreview> getMovies() {
		return movies;
	}

	public void setMovies(List<MoviePreview> movies) {
		this.movies = movies;
	}
}
