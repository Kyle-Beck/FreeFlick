package com.moviestreaming.springsecurity.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moviestreaming.springsecurity.entity.History;

@Repository
public class HistoryDaoImpl implements HistoryDao {

	@Autowired
	private EntityManager session;

	@Transactional
	@Override
	public void saveOrUpdate(int movieID, String username, boolean liked) {

		// query user history to check if they have already rated this movie
		TypedQuery<History> theQuery = session.createQuery(
				"from History where movieID = ?1 and username = ?2", History.class);
		theQuery.setParameter(1, movieID);
		theQuery.setParameter(2, username);
		
		List<History> histories = theQuery.getResultList();

		// if user has rated this movie already... else...
		if (histories.size() > 0) {
			histories.get(0).setLiked(liked);
		} else {
			History history = new History(movieID, username, liked);
			session.merge(history);
		}
	}
	
	@Transactional
	@Override
	public int getRating(String movieID, String username) {
		TypedQuery<History> theQuery = session.createQuery(
				"from History where movieID = ?1 and username = ?2", History.class);
		theQuery.setParameter(1, Integer.parseInt(movieID));
		theQuery.setParameter(2, username);
		
		List<History> histories = theQuery.getResultList();
		if (histories.size() == 0) {
			return -1;
		} else {
			if(histories.get(0).isLiked()) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}

