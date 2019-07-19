package com.btineo.netflixTakehome.dao;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Contains the information of an episode entry as provided by IMBD
 *
 */
@JsonInclude(Include.NON_NULL)
public class Rating {



	private String tconst;
	

	private double averageRating;
	

	private int numVotes;
	


	public Rating(){
		
	}
	
	public Rating(String tconst, double averageRating, int numVotes){
		this.tconst = tconst;
		this.averageRating = averageRating;
		this.numVotes = numVotes;

	}

	public String getTconst() {
		return tconst;
	}

	public void setTconst(String tconst) {
		this.tconst = tconst;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public int getNumVotes() {
		return numVotes;
	}

	public void setNumVotes(int numVotes) {
		this.numVotes = numVotes;
	}
}