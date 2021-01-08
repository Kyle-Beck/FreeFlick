package com.moviestreaming.springsecurity.dto;

// Ratings are used in the RecommendationService

public class Rating implements Comparable<Rating> {
	private Integer movieID;
	private Double weight;

	public Rating(Integer id, Double weight) {
		this.movieID = id;
		this.weight = weight;
	}
	
	// DTO needs default constructor.
	public Rating() {
	}

	public Integer getMovieID() {
		return movieID;
	}

	public void setMovieID(Integer movieID) {
		this.movieID = movieID;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	// inverse the compareTo so that higher rated movies are in the front after we sort
	public int compareTo(Rating other) {
		return other.weight.compareTo(this.weight);
	}

}