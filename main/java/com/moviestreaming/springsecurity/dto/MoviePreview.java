package com.moviestreaming.springsecurity.dto;

// Users click on movie previews that link to the /watch page with movieID as a parameter

public class MoviePreview implements Comparable<MoviePreview> {
	private int movieID;
	private String title;
	private int year;
	private String posterUrl;
	private Double score;

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public int getMovieID() {
		return movieID;
	}

	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	// inverse compareTo so the highest rated movies are first after sorting
	public int compareTo(MoviePreview other) {
		return other.getScore().compareTo(this.getScore());
	}

}
