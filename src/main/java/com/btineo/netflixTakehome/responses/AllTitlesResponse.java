package com.btineo.netflixTakehome.responses;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Contains the information of a Title as provided by IMBD
 *
 */
@JsonInclude(Include.NON_NULL)
public class AllTitlesResponse {


	private String id;
	

	private String title;
		
	

	private String year;
	
	
	private String type;

	


	public AllTitlesResponse(){
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AllTitlesResponse(String title, String year, String id, String type){
		this.id = id;
		this.title = title;
		this.year = year;
		this.type = type;

	}
}