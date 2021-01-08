package com.moviestreaming.springsecurity.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moviestreaming.springsecurity.entity.MovieDetails;

@Repository
public class MovieDetailsDaoImpl implements MovieDetailsDao {
	@Autowired
	private EntityManager session;

	@Transactional
	@Override
	public MovieDetails findById(int id) {
		return session.find(MovieDetails.class, id);
	}
}
