package com.btineo.netflixTakehome.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Contains the information of a Title as provided by IMBD
 *
 */
@JsonInclude(Include.NON_NULL)
public class PrincipalDTO {


	private String tconst;


	private String ordering;
	

	private String nconst;
	

	private String category;
	

	private String job;
		
	
	private String characters;
	
	
	public PrincipalDTO() {
		
	}

	public PrincipalDTO(String tconst, String ordering, String nconst, String category, String job,
			String characters) {
		super();
		this.tconst = tconst;
		this.ordering = ordering;
		this.nconst = nconst;
		this.category = category;
		this.job = job;
		this.characters = characters;
	}

	public String getTconst() {
		return tconst;
	}

	public void setTconst(String tconst) {
		this.tconst = tconst;
	}

	public String getOrdering() {
		return ordering;
	}

	public void setOrdering(String ordering) {
		this.ordering = ordering;
	}

	public String getNconst() {
		return nconst;
	}

	public void setNconst(String nconst) {
		this.nconst = nconst;
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

	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	
	
}