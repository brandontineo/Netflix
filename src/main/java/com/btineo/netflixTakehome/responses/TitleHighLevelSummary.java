package com.btineo.netflixTakehome.responses;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.btineo.netflixTakehome.dao.CrewMember;
import com.btineo.netflixTakehome.dao.Season;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Contains the response we display for a TV show summary
 *
 */
@JsonInclude(Include.NON_NULL)
public class TitleHighLevelSummary {


	private String showId;
	
	private String showName;
	
	private String year;

	private Double averageRating;
	
	private Integer numberOfVotes;


	private List<CrewMember> crewMembers;
	

	public TitleHighLevelSummary(){
		
	}


	public TitleHighLevelSummary(String showId, String showName, String year, 
			Double averageRating, Integer numberOfVotes, List<CrewMember> crewMembers) {
		this.showId = showId;
		this.showName = showName;
		this.year = year;
		this.averageRating = averageRating;
		this.numberOfVotes = numberOfVotes;
		this.setCrewMembers(crewMembers);
	}


	public String getShowId() {
		return showId;
	}


	public void setShowId(String showId) {
		this.showId = showId;
	}


	public String getShowName() {
		return showName;
	}


	public void setShowName(String showName) {
		this.showName = showName;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public Double getAveragrRating() {
		return averageRating;
	}


	public void setAveragrRating(Double averageRating) {
		this.averageRating = averageRating;
	}


	public Integer getNumberOfVotes() {
		return numberOfVotes;
	}


	public void setNumberOfVotes(Integer numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}


	public List<CrewMember> getCrewMembers() {
		return crewMembers;
	}


	public void setCrewMembers(List<CrewMember> crewMembers) {
		this.crewMembers = crewMembers;
	}


	

}