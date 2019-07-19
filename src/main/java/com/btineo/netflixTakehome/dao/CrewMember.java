package com.btineo.netflixTakehome.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Contains the data access object for an episode
 * @author btineo
 *
 */
@JsonInclude(Include.NON_NULL)
public class CrewMember {
	
	private String crewMemberId;
	
	private String category;
	
	private String job;

	private List<String> characterNames;

	
	public CrewMember() {
		
	}
	
	public CrewMember(String crewMemberId, String category, String job, List<String> characterNames) {
		this.crewMemberId = crewMemberId;
		this.category = category;
		this.job = job;
		this.characterNames = characterNames;
	}

	public String getCrewMemberId() {
		return crewMemberId;
	}

	public void setCrewMemberId(String crewMemberId) {
		this.crewMemberId = crewMemberId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public List<String> getCharacterNames() {
		return characterNames;
	}

	public void setCharacterNames(List<String> characterNames) {
		this.characterNames = characterNames;
	}


}
