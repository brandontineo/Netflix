package com.btineo.netflixTakehome.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Contains the data access object for an episode
 * @author btineo
 *
 */
@JsonInclude(Include.NON_NULL)
public class Episode {
	
	private String episodeId;
	
	private String episodeName;
	
	private Double episodeRating;
	
	private int episodeNumber;


	public String getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}

	public String getEpisodeName() {
		return episodeName;
	}

	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}

	public Double getEpisodeRating() {
		return episodeRating;
	}

	public void setEpisodeRating(Double episodeRating) {
		this.episodeRating = episodeRating;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public Episode(String episodeId, String episodeName, Double episodeRating, int episodeNumber) {
		super();
		this.episodeId = episodeId;
		this.episodeName = episodeName;
		this.episodeRating = episodeRating;
		this.episodeNumber = episodeNumber;
	}
	
	public Episode(String episodeId, int episodeNumber) {
		super();
		this.episodeId = episodeId;
		this.episodeNumber = episodeNumber;

	}

	public Episode() {

	}

	@Override
	public String toString() {
		return "Episode [episodeId=" + episodeId + ", episodeName=" + episodeName + ", episodeRating=" + episodeRating
				+ "]";
	}


}
