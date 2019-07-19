package com.btineo.netflixTakehome.exceptions;


public class GlobalException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String suggestion = null;
	
	public GlobalException() {
		super();
	}
	
	public GlobalException(String msg) {
		super(msg);
	}
	
	public GlobalException(String msg, String suggestion) {
		super(msg);
		this.setSuggestion(suggestion);
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

}
