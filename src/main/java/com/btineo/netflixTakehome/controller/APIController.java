package com.btineo.netflixTakehome.controller;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.btineo.netflixTakehome.batch.processors.EpisodesProcessor;
import com.btineo.netflixTakehome.dao.CrewMember;
import com.btineo.netflixTakehome.dao.Episode;
import com.btineo.netflixTakehome.dao.Rating;
import com.btineo.netflixTakehome.dao.Season;
import com.btineo.netflixTakehome.dao.ShowRow;
import com.btineo.netflixTakehome.exceptions.GlobalException;
import com.btineo.netflixTakehome.responses.AllTitlesResponse;
import com.btineo.netflixTakehome.responses.TitleHighLevelSummary;
import com.btineo.netflixTakehome.responses.TitleGranularSummary;
import com.btineo.netflixTakehome.utils.JDBCServiceUtil;
import com.btineo.netflixTakehome.utils.MapperUtils;


@RestController
public class APIController {

	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private JDBCServiceUtil jdbcUtil;
	
    private static final Logger log = LoggerFactory.getLogger(EpisodesProcessor.class);


	@RequestMapping(value =  "/titles", method = RequestMethod.GET)
	Collection<AllTitlesResponse> getTitles() {
		List<AllTitlesResponse> results = jdbcUtil.retrieveAllTitles(jdbcTemplate);
		return results;
	}
	
	@RequestMapping(value = "/title", method = RequestMethod.GET, params = {"id"})
	TitleHighLevelSummary getTitleSummary(@RequestParam(value = "id") String id) throws GlobalException, InterruptedException, ExecutionException {
		
		// Run these in parallel since they don't depend on each other :D 
		Future<List<AllTitlesResponse>> titlesResultsFuture = jdbcUtil.retrieveTitleBasics(jdbcTemplate, id);
		Future<List<Rating>> ratingsResultsFuture = jdbcUtil.retrieveRatings(jdbcTemplate, id);
		Future<List<CrewMember>> crewMembersFuture = jdbcUtil.retrieveCrew(jdbcTemplate, id);
		
		// Get the results from the futures when they're ready
		List<AllTitlesResponse> titlesResults = titlesResultsFuture.get();
		List<Rating> ratingsResults = ratingsResultsFuture.get();
		List<CrewMember> crewMembers = crewMembersFuture.get();
		
		// Map the results
		TitleHighLevelSummary summary = MapperUtils.mapTitleRatingsCrewDataToShow(titlesResults, ratingsResults, crewMembers);
		return summary;
	}


	@RequestMapping(value = "/child-episodes-by-parent-id", method = RequestMethod.GET, params = {"id"})
	TitleGranularSummary getChildEpisodesByParentId(@RequestParam(value = "id") String id) {		
		List<ShowRow> results = jdbcUtil.retrieveEpisodesByParentId(jdbcTemplate, id);
		TitleGranularSummary tvShowSummary = MapperUtils.mapShowRowsToShowSummary(results);
		return tvShowSummary;
	}	
	
	@RequestMapping(value = "/season-rating", method = RequestMethod.GET, params = {"id", "seasonNumber"})
	Season getSeasonRating(@RequestParam(value = "id") String id, @RequestParam(value = "seasonNumber") int seasonNumber) throws GlobalException {
		
		
		System.out.println(id);
		System.out.println(seasonNumber);

		
		// TODO: Run in parallel
		List<Episode> episodesFound = jdbcUtil.retrievesEpisodeIds(jdbcTemplate, id, seasonNumber);
		double rating = jdbcUtil.retrieveSeasonRating(jdbcTemplate, episodesFound);
		
		Season season = new Season(episodesFound, rating, seasonNumber);
		return season;
	}


	

	
}