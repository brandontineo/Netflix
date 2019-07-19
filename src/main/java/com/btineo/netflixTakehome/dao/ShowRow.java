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
public class ShowRow {

	private double seasonRating;
	private String tconst;
	private String parentTconst;
	private String primaryTitle;
	private double episodeRating;
	private int seasonNumber;
	private int episodeNumber;
	private int year;
	private String episodeName;

	public ShowRow(String tconst, String parentTconst, String primaryTitle, int episodeRating, 
			int seasonNumber, int episodeNumber, int year, String episodeName) {
		this.tconst = tconst;
		this.parentTconst = parentTconst;
		this.primaryTitle = primaryTitle;
		this.episodeRating = episodeRating;
		this.seasonNumber = seasonNumber;
		this.episodeNumber = episodeNumber;
		this.year = year;
		this.setEpisodeName(episodeName);
		
	}

	public double getSeasonRating() {
		return seasonRating;
	}

	public void setSeasonRating(double seasonRating) {
		this.seasonRating = seasonRating;
	}

	public String getTconst() {
		return tconst;
	}

	public void setTconst(String tconst) {
		this.tconst = tconst;
	}

	public String getParentTconst() {
		return parentTconst;
	}

	public void setParentTconst(String parentTconst) {
		this.parentTconst = parentTconst;
	}

	public String getPrimaryTitle() {
		return primaryTitle;
	}

	public void setPrimaryTitle(String primaryTitle) {
		this.primaryTitle = primaryTitle;
	}

	public double getEpisodeRating() {
		return episodeRating;
	}

	public void setEpisodeRating(int episodeRating) {
		this.episodeRating = episodeRating;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getEpisodeName() {
		return episodeName;
	}

	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}

	@Override
	public String toString() {
		return "ShowRow [seasonRating=" + seasonRating + ", tconst=" + tconst + ", parentTconst=" + parentTconst
				+ ", primaryTitle=" + primaryTitle + ", episodeRating=" + episodeRating + ", seasonNumber="
				+ seasonNumber + ", episodeNumber=" + episodeNumber + ", year=" + year + "] \n\n";
	}


}