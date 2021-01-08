package com.moviestreaming.springsecurity.service;

import java.util.List;

import com.moviestreaming.springsecurity.dto.MoviePreview;

// finds a users liked history for the WatchHistory page

public interface WatchHistory {
	public List<MoviePreview> getLikedHistory(String username);
}
