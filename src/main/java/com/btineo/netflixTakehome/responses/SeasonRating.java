package com.btineo.netflixTakehome.responses;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.btineo.netflixTakehome.dao.Season;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Contains the response we display for a TV show summary
 *
 */
@JsonInclude(Include.NON_NULL)
public class SeasonRating {


	private String showId;
	
	private int seasonNumber;
	
	private double seasonRating;
	
	private int numberOfEpisodes;
	
	public SeasonRating(String showId, int seasonNumber, double seasonRating, int numberOfEpisodes) {
		super();
		this.showId = showId;
		this.seasonNumber = seasonNumber;
		this.seasonRating = seasonRating;
		this.numberOfEpisodes = numberOfEpisodes;
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public double getSeasonRating() {
		return seasonRating;
	}

	public void setSeasonRating(double seasonRating) {
		this.seasonRating = seasonRating;
	}

	public int getNumberOfEpisodes() {
		return numberOfEpisodes;
	}

	public void setNumberOfEpisodes(int numberOfEpisodes) {
		this.numberOfEpisodes = numberOfEpisodes;
	}



}