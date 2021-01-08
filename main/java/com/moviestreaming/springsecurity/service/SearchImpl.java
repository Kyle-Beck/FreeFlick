package com.moviestreaming.springsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviestreaming.springsecurity.dto.MoviePreview;
import com.moviestreaming.springsecurity.entity.MovieDetails;
import com.moviestreaming.springsecurity.repository.MovieDetailsDao;
import com.moviestreaming.springsecurity.repository.MovieDetailsDaoImpl;
import com.moviestreaming.springsecurity.repository.MoviePreviewDao;

@Service
public class SearchImpl implements Search {
	@Autowired
	MoviePreviewDao moviePreviewDao;

	@Override
	public List<MoviePreview> getSearch(String search, String startYear, String endYear, String genre) {
		search = (search == null) ? "" : search;

		// if any filter parameters were provided, return findFilteredSearchResults(...)
		if (startYear != null || endYear != null || genre != null) {
			// if any parameters were not provided, give them default values
			int start = (startYear == null || startYear.contentEquals("")) ? 0 : Integer.parseInt(startYear);
			int end = (endYear == null || endYear.contentEquals("")) ? 2050 : Integer.parseInt(endYear);
			String formattedGenre = (genre == null) ? "" : genre;
			return moviePreviewDao.findFilteredSearchResults(search, start, end, formattedGenre);
		}

		return moviePreviewDao.findSearchResults(search);
	}

}
