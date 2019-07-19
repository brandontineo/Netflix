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
public class TVShowSummary {

	

	private String showId;
	
	private String showName;


	private List<Season> seasons;
	
	private int year;


	public TVShowSummary(){
		
	}


	public String getShowId() {
		return showId;
	}


	public void setShowId(String showId) {
		this.showId = showId;
	}


	public List<Season> getSeasons() {
		return seasons;
	}


	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public String getShowName() {
		return showName;
	}


	public void setShowName(String showName) {
		this.showName = showName;
	}


	@Override
	public String toString() {
		return "TVShowSummary [showId=" + showId + ", seasons=" + seasons + "]";
	}
	

}