package com.btineo.netflixTakehome.controller;

import java.util.Collection;
import java.util.List;
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
import com.btineo.netflixTakehome.responses.TVShowHighLevelSummary;
import com.btineo.netflixTakehome.responses.TVShowSummary;
import com.btineo.netflixTakehome.utils.JDBCUtil;
import com.btineo.netflixTakehome.utils.MapperUtils;


@RestController
public class APIController {

	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
    private static final Logger log = LoggerFactory.getLogger(EpisodesProcessor.class);


	@RequestMapping(value =  "/titles", method = RequestMethod.GET)
	Collection<AllTitlesResponse> getTitles() {
		List<AllTitlesResponse> results = JDBCUtil.retrieveAllTitles(jdbcTemplate);
		return results;
	}
	
	@RequestMapping(value = "/title", method = RequestMethod.GET, params = {"tvShowId"})
	TVShowHighLevelSummary getTitleSummary(@RequestParam(value = "tvShowId") String tvShowId) throws GlobalException {
		
		// TODO: Run in parallel
		List<AllTitlesResponse> titlesResults = JDBCUtil.retrieveTitleBasics(jdbcTemplate, tvShowId);
		List<Rating> ratingsResults = JDBCUtil.retrieveRatings(jdbcTemplate, tvShowId);
		List<CrewMember> crewMembers = JDBCUtil.retrieveCrew(jdbcTemplate, tvShowId);
		
		// Map the results
		TVShowHighLevelSummary summary = MapperUtils.mapTitleRatingsCrewDataToShow(titlesResults, ratingsResults, crewMembers);
		return summary;
	}


	@RequestMapping(value = "/child-episodes-by-parent-id", method = RequestMethod.GET, params = {"tvShowId"})
	TVShowSummary getChildEpisodesByParentId(@RequestParam(value = "tvShowId") String tvShowId) {		
		List<ShowRow> results = JDBCUtil.retrieveEpisodesByParentId(jdbcTemplate, tvShowId);
		TVShowSummary tvShowSummary = MapperUtils.mapShowRowsToShowSummary(results);
		return tvShowSummary;
	}	
	
	@RequestMapping(value = "/season-rating", method = RequestMethod.GET, params = {"tvShowId", "seasonNumber"})
	Season getSeasonRating(@RequestParam(value = "tvShowId") String tvShowId, @RequestParam(value = "seasonNumber") int seasonNumber) throws GlobalException {
		
		
		System.out.println(tvShowId);
		System.out.println(seasonNumber);

		
		// TODO: Run in parallel
		List<Episode> episodesFound = JDBCUtil.retrievesEpisodeIds(jdbcTemplate, tvShowId, seasonNumber);
		double rating = JDBCUtil.retrieveSeasonRating(jdbcTemplate, episodesFound);
		
		Season season = new Season(episodesFound, rating, seasonNumber);
		return season;
	}


	

	
}