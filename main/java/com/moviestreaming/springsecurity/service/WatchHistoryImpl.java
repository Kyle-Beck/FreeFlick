package com.moviestreaming.springsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviestreaming.springsecurity.dto.MoviePreview;
import com.moviestreaming.springsecurity.repository.MoviePreviewDao;

@Service
public class WatchHistoryImpl implements WatchHistory {

	@Autowired
	MoviePreviewDao moviePreviewDao;

	public List<MoviePreview> getLikedHistory(String username) {
		return moviePreviewDao.findLikedHistory(username);
	}
}
