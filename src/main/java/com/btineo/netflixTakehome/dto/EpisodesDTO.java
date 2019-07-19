package com.btineo.netflixTakehome.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Contains the information of an episode entry as provided by IMBD
 *
 */
@JsonInclude(Include.NON_NULL)
public class EpisodesDTO {



	private String tconst;
	

	private String parentTconst;
	

	private int seasonNumber;
	

	private int episodeNumber;
	



	public EpisodesDTO(){
		
	}
	
	public EpisodesDTO(String tconst, String parentTconst, int seasonNumber,
			int episodeNumber){
		this.tconst = tconst;
		this.parentTconst = parentTconst;
		this.seasonNumber = seasonNumber;
		this.episodeNumber = episodeNumber;

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

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("tconst", this.tconst)
				.append("parentTConst", this.parentTconst)
				.append("episodeNumber", this.episodeNumber)
				.append("seasonNumber", this.seasonNumber)
				.toString();
	}
}