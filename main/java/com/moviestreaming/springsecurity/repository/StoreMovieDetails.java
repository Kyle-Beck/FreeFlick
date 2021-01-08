package com.moviestreaming.springsecurity.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moviestreaming.springsecurity.entity.MovieDetails;
import com.moviestreaming.springsecurity.entity.Tag;

//TODO: Delete this class

@Repository
public class StoreMovieDetails {

	@Autowired
	EntityManager session;

	//TODO: clear db and add movies
	@Transactional
	public void saveMovie() throws ParseException, IOException {
		System.out.println("START");
		int i = 0;
		ArrayList<MovieDetails> movies = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("CENSORED FOR SECURITY"));
		
		
		try {
			while (true) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				
				String[] splitLine = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				String fileTitle = splitLine[0].trim();
				int fileYear = Integer.parseInt(splitLine[1].trim());
				String fileEmbed = splitLine[2].trim();
				
				System.out.println();
				System.out.println(fileTitle);
				
				String formattedTitle = fileTitle.trim().replace(" ", "+");
				URL url = new URL("http://www.omdbapi.com/?t=" + formattedTitle + "&y=" + fileYear + "&plot=full&apikey=CENSORED FOR SECURITY");
	
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("accept", "application/json");
	
				// This line makes the request
				InputStream responseStream = connection.getInputStream();
				JSONParser parser = new JSONParser();
				JSONObject jo = (JSONObject) parser.parse(new InputStreamReader(responseStream, "UTF-8"));
	
				// Create MovieDetails and set its values
	
				MovieDetails movieDetails = new MovieDetails();
	
				movieDetails.setEmbed(fileEmbed);
				movieDetails.setTitle(jo.get("Title").toString());
				movieDetails.setPlot(jo.get("Plot").toString());
				movieDetails.setPosterUrl(jo.get("Poster").toString());
				if(!(jo.get("Runtime").toString().contentEquals("N/A"))) {
					movieDetails.setRuntime(Integer.parseInt(jo.get("Runtime").toString().split(" ")[0]));
				}else {
					movieDetails.setRuntime(0);
				}
				movieDetails.setYear(Integer.parseInt(jo.get("Year").toString()));
				
				Tag yearTag = new Tag(jo.get("Year").toString().trim(), "year");
				movieDetails.add(yearTag);
	
				// Create tags for the movie
				parseTags("Actors", "actor", jo, movieDetails);		
				parseTags("Writer", "writer", jo, movieDetails);
				parseTags("Director", "director", jo, movieDetails);		
				parseTags("Genre", "genre", jo, movieDetails);		
				parseTags("Production", "production", jo, movieDetails);
				parseTags("Language", "language", jo, movieDetails);
				parseTags("Country", "country", jo, movieDetails);
				
				// add to result 
				movies.add(movieDetails);
	
			}
			for(MovieDetails m: movies) {
				this.session.merge(m);
			}
		}finally {
			br.close();
		}
	}
	
	public void parseTags(String jsonAttribute, String tagType, JSONObject jo, MovieDetails movieDetails) {
		String json = jo.get(jsonAttribute).toString();
		String[] tagList = json.split(",");
		for (String tag : tagList) {
			if (!tag.contentEquals("N/A")) {
				Tag tempTag = new Tag(tag.trim(), tagType);
				movieDetails.add(tempTag);
			}
		}
	}
	
}
