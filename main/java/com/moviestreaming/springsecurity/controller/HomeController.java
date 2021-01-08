package com.moviestreaming.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moviestreaming.springsecurity.entity.MovieDetails;
import com.moviestreaming.springsecurity.container.MovieBatch;
import com.moviestreaming.springsecurity.dto.MoviePreview;
import com.moviestreaming.springsecurity.repository.HistoryDao;
import com.moviestreaming.springsecurity.repository.RandomMovieDAO;
import com.moviestreaming.springsecurity.service.Recommendation;
import com.moviestreaming.springsecurity.service.Search;
import com.moviestreaming.springsecurity.service.WatchHistory;
import com.moviestreaming.springsecurity.service.WatchPage;

@Controller
public class HomeController {

	@Autowired
	Recommendation recommendationService;
	@Autowired
	Search searchService;
	@Autowired
	WatchPage watchPageService;
	@Autowired
	WatchHistory watchHistoryService;
	@Autowired
	RandomMovieDAO randomDao;
	@Autowired
	HistoryDao historyDao;

	@GetMapping("/")
	public String home(Model theModel, @AuthenticationPrincipal OAuth2User principal) {
		String username = principal.getName();
		List<MovieBatch> recs = recommendationService.getHomePreviews(username);
		theModel.addAttribute("recs", recs);
		return ("homeBootstrap");
	}

	@GetMapping("/watch")
	public String user(@AuthenticationPrincipal OAuth2User principal, Model theModel, @RequestParam String id) {
		MovieDetails movie = watchPageService.findById(Integer.parseInt(id));
		theModel.addAttribute("movie", movie);

		String username = principal.getName();
		MovieBatch recommendations = recommendationService.getWatchRecommendations(username, id);
		theModel.addAttribute("recommendations", recommendations);
		
		int rating = historyDao.getRating(id, principal.getName());
		theModel.addAttribute("existingRating", rating);

		return "watchBootstrap";
	}

	@GetMapping("/random")
	public String user(@AuthenticationPrincipal OAuth2User principal) {
		// To get a random movie, forward user to /watch with a random unseen id
		String username = principal.getName();
		return "forward:/watch?id=" + randomDao.getRandomID(username);
	}

	@GetMapping("/search")
	public String user(@RequestParam(required = false) String search, @RequestParam(required = false) String startYear,
			@RequestParam(required = false) String endYear, @RequestParam(required = false) String genre,
			Model theModel) {

		List<MoviePreview> results = searchService.getSearch(search, startYear, endYear, genre);
		theModel.addAttribute("results", results);
		return "searchBootstrap";
	}

	@GetMapping("/legal")
	public String legal(@RequestParam(required = false) String doc) {
		// This controller handles requests for all html legal files
		// If a parameter is present, serve corresponding legal doc. Otherwise, serve
		// legal docs directory
		switch (doc) {
		case "privacy":
			return "privacyPolicy";
		case "tos":
			return "tos";
		default:
			return "legal";
		}
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/watchHistory")
	public String watchHistory(@AuthenticationPrincipal OAuth2User principal, Model theModel) {
		String username = principal.getName();
		List<MoviePreview> results = watchHistoryService.getLikedHistory(username);
		theModel.addAttribute("movies", results);
		return "watchHistoryBootstrap";
	}

	@GetMapping("/saveHistory")
	public void saveHistory(@AuthenticationPrincipal OAuth2User principal, @RequestParam String rating,
			@RequestParam String movieId) {
		//System.out.println(rating);
		String username = principal.getName();
		//TODO: change liked to an int for entity/table
		boolean liked = (rating.contentEquals("1")) ? true : false;
		int movieIdInt = Integer.parseInt(movieId);
		historyDao.saveOrUpdate(movieIdInt, username, liked);
	}
}
