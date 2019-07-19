package com.btineo.netflixTakehome.dao;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Contains the data access object of a season
 *
 */
@JsonInclude(Include.NON_NULL)
public class Season {

	private List<Episode> episodes;
	private double seasonRating;
	private int seasonNumber;
	

	public Season(List<Episode> episodes, double seasonRating, int seasonNumber) {
		super();
		this.episodes = episodes;
		this.seasonRating = seasonRating;
		this.seasonNumber = seasonNumber;
	}

	public Season() {

	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public double getSeasonRating() {
		return seasonRating;
	}

	public void setSeasonRating(double seasonRating) {
		this.seasonRating = seasonRating;
	}
	
	public int getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	@Override
	public String toString() {
		return "Season [episodes=" + episodes + ", seasonRating=" + seasonRating + "]";
	}


}