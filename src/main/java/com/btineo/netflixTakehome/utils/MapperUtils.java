package com.btineo.netflixTakehome.utils;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.btineo.netflixTakehome.constants.ErrorConstants;
import com.btineo.netflixTakehome.dao.CrewMember;
import com.btineo.netflixTakehome.dao.Episode;
import com.btineo.netflixTakehome.dao.Rating;
import com.btineo.netflixTakehome.dao.Season;
import com.btineo.netflixTakehome.dao.ShowRow;
import com.btineo.netflixTakehome.exceptions.ResourceNotFoundException;
import com.btineo.netflixTakehome.responses.AllTitlesResponse;
import com.btineo.netflixTakehome.responses.TVShowHighLevelSummary;
import com.btineo.netflixTakehome.responses.TVShowSummary;

public class MapperUtils {

	
    private static final Logger log = LoggerFactory.getLogger(MapperUtils.class);

	
	
	/**
	 * Method to convert a non standard string version of an array to an ArrayList
	 * @param stringArr
	 * @return
	 */
	public static List<String> convertStringToArrayList(String stringArr) {
		List<String> convertedList = new ArrayList<String>();
		try {
	        JSONArray jsonArr = new JSONArray(stringArr);  
	        if (jsonArr != null) { 
		           for (int i=0;i<jsonArr.length();i++){ 
		        	   convertedList.add(jsonArr.getString(i));
		           } 
		        }
        } catch (Exception e) {
        	log.warn("Invalid stringArr found " + stringArr);
        }
		return convertedList;
	}
	
	
	
	/**
	 * Maps a rows of DAOs to the response we will send API caller
	 * @param results
	 * @return
	 */
	public static TVShowSummary mapShowRowsToShowSummary(List<ShowRow> results) {
		
		// if results is empty we can return 404 gracefully since it means no results found
		TVShowSummary summaryResponse  = new TVShowSummary();
		
		if (CollectionUtils.isNotEmpty(results)) {
			ShowRow firstResult = results.get(0);
			summaryResponse.setShowId(firstResult.getParentTconst());
			summaryResponse.setShowName(firstResult.getPrimaryTitle());
			summaryResponse.setYear(firstResult.getYear());		
			
			List<Season> allSeasons = new ArrayList<Season>();

			
			int seasonNumber = 1;
			List<Episode> episodes = new ArrayList<Episode>();
			Season season = new Season();
			season.setSeasonNumber(seasonNumber);
						
			for (ShowRow row : results) {
				
				Episode e = new Episode();
				e.setEpisodeId(row.getTconst());
				
				String episodeName = row.getEpisodeName() == null ? "N/A" : row.getEpisodeName();
				e.setEpisodeName(episodeName);
				e.setEpisodeRating(row.getEpisodeRating());
				e.setEpisodeNumber(row.getEpisodeNumber());
				
				int episodesSeasonNumber = row.getSeasonNumber();
				if (seasonNumber != episodesSeasonNumber) {
					
					// store episodes into season and the complete season into allSeasons
					if (!CollectionUtils.isEmpty(episodes)) {
						season.setEpisodes(episodes);
						allSeasons.add(season);						
					}

					
					// wipe out
					seasonNumber = episodesSeasonNumber;
					season = new Season();
					season.setSeasonNumber(seasonNumber);
					episodes = new ArrayList<Episode>();
					episodes.add(e);
					
				} else {
					episodes.add(e);
				}
				
				
			}
			
			// add the last season..we need a way to check if there is actually another season
			season.setEpisodes(episodes);
			allSeasons.add(season);
			
			
			summaryResponse.setSeasons(allSeasons);
			
			
			return summaryResponse;
		}
			


		return null;
	}
	

	public static TVShowHighLevelSummary mapTitleRatingsCrewDataToShow(List<AllTitlesResponse> titlesResults, List<Rating> ratingsResults,
			List<CrewMember> crewMembers) throws ResourceNotFoundException {
		
		
		if (titlesResults == null || titlesResults.isEmpty()) {
			throw new ResourceNotFoundException("No movies found.", ErrorConstants.TRY_A_DIFFERENT_MOVIE_ID);

		}
		
		Double averageRatings = null;
		Integer numberOfVotes = null;
		if (ratingsResults != null && !ratingsResults.isEmpty()) {
			Rating firstRating = ratingsResults.get(0);
			averageRatings = firstRating.getAverageRating();
			numberOfVotes = firstRating.getNumVotes();		
		}
		
		AllTitlesResponse firstTitle = titlesResults.get(0);
		
		TVShowHighLevelSummary summary = new TVShowHighLevelSummary(firstTitle.getId(), firstTitle.getTitle(),
				firstTitle.getYear(), averageRatings, numberOfVotes, crewMembers);
		return summary;
	}



	/**
	 * Converts a list of episodes into a string of episode IDs that SQL understands
	 * @param episodesFound
	 * @return
	 */
	public static String getEpisodeIdsAsSQLClause(List<Episode> episodesFound) {
		
		if (episodesFound.isEmpty()) {
			return null;
		}
		
		
		boolean isFirst = true;
		StringBuilder str = new StringBuilder("(");
		for (Episode ep : episodesFound) {
			
			if (!isFirst) {
				str.append(",");				
			}
			isFirst = false;

			str.append("'");
			str.append(ep.getEpisodeId());
			str.append("'");
		}
		str.append(")");
		
		return str.toString();
		
		
	}

	
	
}
