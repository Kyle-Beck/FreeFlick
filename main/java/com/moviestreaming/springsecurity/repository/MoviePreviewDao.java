package com.moviestreaming.springsecurity.repository;

import java.util.HashSet;
import java.util.List;
import com.moviestreaming.springsecurity.dto.MoviePreview;
import com.moviestreaming.springsecurity.dto.Rating;
import com.moviestreaming.springsecurity.enums.TagType;

public interface MoviePreviewDao {
	// get popular movie previews for home page
	public List<MoviePreview> findPopular();

	// used to get categories for home page
	public List<MoviePreview> findUnseenByTag(TagType tagType, String tagName, int limit, String username, HashSet<Integer> ids);

	// get results for search bar on home page
	public List<MoviePreview> findSearchResults(String searchString);

	// get results for advanced search on search page
	public List<MoviePreview> findFilteredSearchResults(String searchString, int startYear, int endYear, String genre);

	// get the users liked history for the watchHistory page
	public List<MoviePreview> findLikedHistory(String username);

	// get movies from a list of Ratings for the RecommendationService class
	public List<MoviePreview> findByRating(List<Rating> ratings);
}
