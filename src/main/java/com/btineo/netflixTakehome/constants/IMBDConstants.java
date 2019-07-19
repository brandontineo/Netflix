package com.btineo.netflixTakehome.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IMBDConstants {
	
	
	public static final String EMPTY_SYMBOL = "\\N";
	public static final String UNKNOWN = "UNKNOWN";
    public static final int START_YEAR_OF_INTEREST = 2018;
    public static final List<String> TYPES_OF_INTEREST = new ArrayList<String>(Arrays.asList("movie", "tvSeries"));

	
	// For testing
	public static final String RATINGS_FILE = "sample-title.ratings.tsv";
	public static final String TITLES_FILE = "sample-title.basics.tsv";
	public static final String PRINCIPALS_FILE = "sample-title.principals.tsv";
	public static final String EPSIODES_FILE = "sample-title.episode.tsv";

	// The real deal
//	public static final String RATINGS_FILE = "title.ratings.tsv";
//	public static final String TITLES_FILE = "title.basics.tsv";
//	public static final String PRINCIPALS_FILE = "title.principals.tsv";
//	public static final String EPSIODES_FILE = "title.episode.tsv";
	

}
