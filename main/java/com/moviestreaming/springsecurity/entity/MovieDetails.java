package com.moviestreaming.springsecurity.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// MovieDetails contains the movie embed-code and movie details that are displayed on the /watch page

@Entity
@Table(name = "movie_details")
public class MovieDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int movieID;
	private String title;
	private int year;
	private int runtime;
	private String plot;
	private String posterUrl;
	private String embed;
	@OneToMany(mappedBy = "movieDetails", cascade = CascadeType.ALL)
	private List<Tag> tags;

	public MovieDetails() {
	}

	public MovieDetails(String title, int year, int runtime, String plot, String posterUrl, String embed) {
		super();
		this.title = title;
		this.year = year;
		this.runtime = runtime;
		this.plot = plot;
		this.posterUrl = posterUrl;
		this.embed = embed;
	}

	public void add(Tag tag) {
		if (this.tags == null) {
			this.tags = new ArrayList<>();
		}

		this.tags.add(tag);
		tag.setMovieDetails(this);
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

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getEmbed() {
		return embed;
	}

	public void setEmbed(String embed) {
		this.embed = embed;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
