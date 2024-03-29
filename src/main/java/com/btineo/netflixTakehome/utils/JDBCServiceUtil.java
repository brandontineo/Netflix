package com.btineo.netflixTakehome.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.btineo.netflixTakehome.constants.ErrorConstants;
import com.btineo.netflixTakehome.dao.CrewMember;
import com.btineo.netflixTakehome.dao.Episode;
import com.btineo.netflixTakehome.dao.Rating;
import com.btineo.netflixTakehome.dao.ShowRow;
import com.btineo.netflixTakehome.exceptions.ResourceNotFoundException;
import com.btineo.netflixTakehome.responses.AllTitlesResponse;

@Service
public class JDBCServiceUtil {

	
    private static final Logger log = LoggerFactory.getLogger(JDBCServiceUtil.class);

	/**
	 * Retrieves cast members from principal table
	 * 
	 * @param jdbcTemplate
	 * @param tvShowId
	 * @return List<CrewMember>  crew members
	 */
    @Async
	public Future<List<CrewMember>> retrieveCrew(JdbcTemplate jdbcTemplate, String tvShowId) {
		
    	log.info("Execute method asynchronously - "
	    	      + Thread.currentThread().getName());
	    
		List<CrewMember> crewMembers = jdbcTemplate.query("SELECT * from principal WHERE tConst ='"+ tvShowId + "' ORDER BY tconst", new RowMapper<CrewMember>() {
			@Override
			public CrewMember mapRow(ResultSet rs, int row) throws SQLException {
				List<String> characters = MapperUtils.convertStringToArrayList(rs.getString("characters"));
				return new CrewMember(
						rs.getString("nconst"),
						rs.getString("category"),
						rs.getString("job"),
						characters
						);
			}

		});
		
		return new AsyncResult<List<CrewMember>>(crewMembers);

	}

	/**
	 * 	Retrieves ratings from rating table
	 * @param jdbcTemplate
	 * @param tvShowId
	 * @return List<Rating> ratings
	 */
    @Async
	public Future<List<Rating>> retrieveRatings(JdbcTemplate jdbcTemplate, String tvShowId) {
    	log.info("Execute method asynchronously - "
	    	      + Thread.currentThread().getName());
		List<Rating> ratingsResults = jdbcTemplate.query("SELECT tconst, averageRating, numVotes from ratings WHERE tConst ='"+ tvShowId + "' ORDER BY tconst", new RowMapper<Rating>() {
			@Override
			public Rating mapRow(ResultSet rs, int row) throws SQLException {
				return new Rating(
						rs.getString("tconst"),
						rs.getDouble("averageRating"),
						rs.getInt("numVotes")

						);
			}
		});
		return new AsyncResult<List<Rating>>(ratingsResults);
	}

	/**
	 * Retrieves that basic information about a title
	 * @param jdbcTemplate
	 * @param tvShowId
	 * @return List<AllTitlesResponse> titles
	 */
    @Async
	public Future<List<AllTitlesResponse>> retrieveTitleBasics(JdbcTemplate jdbcTemplate, String tvShowId) {
    	log.info("Execute method asynchronously - "
	    	      + Thread.currentThread().getName());
		List<AllTitlesResponse> titlesResults = jdbcTemplate.query("SELECT tconst, primaryTitle, startYear, titleType from titles WHERE tConst ='"+ tvShowId + "' ORDER BY tconst", new RowMapper<AllTitlesResponse>() {
			@Override
			public AllTitlesResponse mapRow(ResultSet rs, int row) throws SQLException {
				return new AllTitlesResponse(
						rs.getString("primaryTitle"),
						rs.getString("startYear"),
						rs.getString("tconst"),
						rs.getString("titleType")

						);
			}
		});
		return new AsyncResult<List<AllTitlesResponse>>(titlesResults);
	}
	
	/**
	 * Retrieves that basic information about all titles
	 * @param jdbcTemplate
	 * @return List<AllTitlesResponse>  titles
	 */
	public List<AllTitlesResponse> retrieveAllTitles(JdbcTemplate jdbcTemplate) {
		List<AllTitlesResponse> results = jdbcTemplate.query("SELECT tconst, primaryTitle, startYear, titleType from titles ORDER BY tconst", new RowMapper<AllTitlesResponse>() {
			@Override
			public AllTitlesResponse mapRow(ResultSet rs, int row) throws SQLException {
				return new AllTitlesResponse(
						rs.getString("primaryTitle"),
						rs.getString("startYear"),
						rs.getString("tconst"),
						rs.getString("titleType")
						);
			}
		});
		return results;
	}
	
	
	/**
	 * Retrieves episodes from a specific ID through a join. This is slow and should be removed 
	 * @param jdbcTemplate
	 * @param tvShowId
	 * @return  List<ShowRow> shows
	 */
	public List<ShowRow> retrieveEpisodesByParentId(JdbcTemplate jdbcTemplate, String tvShowId) {
		
		
		String SQLquery = "SELECT "
				+ "E.parentTconst, "
				+ "E.seasonNumber, "
				+ "E.episodeNumber, "
				+ "E.tconst, "
				+ "T.primaryTitle, "
				+ "T.startYear, "
				+ "T2.primaryTitle as episodeTitle "
				+ "from episodes as E "
				+ "LEFT JOIN titles as T ON E.parentTconst = T.tconst "
				+ "LEFT JOIN titles as T2 ON E.tconst = T2.tconst "
				+ "WHERE E.parentTconst = '"+ tvShowId + "' ORDER BY E.tconst";
		
		
		
		List<ShowRow> results = jdbcTemplate.query(SQLquery, new RowMapper<ShowRow>() {
			
			
			@Override
			public ShowRow mapRow(ResultSet rs, int row) throws SQLException {
				
				return new ShowRow(
						rs.getString("tconst"),
						rs.getString("parentTconst"),
						rs.getString("primaryTitle"),
						-1,
						rs.getInt("seasonNumber"),
						rs.getInt("episodeNumber"),
						rs.getInt("startYear"),
						rs.getString("episodeTitle")
						);
			}
		});
		return results;
	}

	
	public List<Episode> retrievesEpisodeIds(JdbcTemplate jdbcTemplate, String tvShowId, int seasonNumber) {
		
		// List of episodes
		List<Episode> episodesForSeason = jdbcTemplate.query("SELECT tconst, episodeNumber from episodes WHERE "
				+ "parentTconst ='"+ tvShowId + "' AND seasonNumber = "+ seasonNumber +" ORDER BY tconst", new RowMapper<Episode>() {
					
					@Override
					public Episode mapRow(ResultSet rs, int row) throws SQLException {
						return new Episode(rs.getString("tconst"), rs.getInt("episodeNumber"));
					}
				});
		
			return episodesForSeason;
	}

	/**
	 * 
	 * @param jdbcTemplate
	 * @param episodesFound
	 * @return double average
	 * @throws ResourceNotFoundException 
	 */
	public double retrieveSeasonRating(JdbcTemplate jdbcTemplate, List<Episode> episodesFound) throws ResourceNotFoundException {
		
		// List of episodes		
		String episodesFoundString = MapperUtils.getEpisodeIdsAsSQLClause(episodesFound);
		if (episodesFoundString == null || episodesFoundString.isEmpty()) {
			throw new ResourceNotFoundException("No episodes with ratings found", ErrorConstants.TRY_A_DIFFERENT_MOVIE_ID);
		}
		
        String sql = "SELECT AVG(averageRating) as c FROM ratings WHERE tconst in " + episodesFoundString;
    	double average = jdbcTemplate.queryForObject(sql, Double.class);
		return average;

	}

}
