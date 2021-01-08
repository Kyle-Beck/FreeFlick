package com.moviestreaming.springsecurity.service;

import java.util.List;

import com.moviestreaming.springsecurity.dto.MoviePreview;
import com.moviestreaming.springsecurity.entity.MovieDetails;

public interface Search {
	public List<MoviePreview> getSearch(String search, String startYear, String endYear, String genre);
}
