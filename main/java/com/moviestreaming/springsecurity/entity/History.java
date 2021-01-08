package com.moviestreaming.springsecurity.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// User history is saved when user clicks on the "like" or "dislike" buttons

@Entity
@Table(name = "History")
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int movieID;
	private String username;
	private boolean liked;

	public History(int movieID, String username, boolean liked) {
		this.movieID = movieID;
		this.username = username;
		this.liked = liked;
	}

	public History() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMovieID() {
		return movieID;
	}

	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}
}