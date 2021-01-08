package com.moviestreaming.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviestreaming.springsecurity.entity.MovieDetails;
import com.moviestreaming.springsecurity.repository.MovieDetailsDao;

@Service
public class WatchPageImpl implements WatchPage {

	@Autowired
	MovieDetailsDao movieDetailsDao;

	@Override
	public MovieDetails findById(int id) {
		return movieDetailsDao.findById(id);
	}

}