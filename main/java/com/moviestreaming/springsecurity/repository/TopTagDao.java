package com.moviestreaming.springsecurity.repository;

import java.util.List;
import com.moviestreaming.springsecurity.dto.TopTag;
import com.moviestreaming.springsecurity.enums.TagType;

// This class is used by RecommendationService to get custom categories for /home page

public interface TopTagDao {
	public List<TopTag> findTopTags(String username, TagType tagType, int limit);
}
