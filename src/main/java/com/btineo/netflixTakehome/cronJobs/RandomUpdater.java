package com.btineo.netflixTakehome.cronJobs;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RandomUpdater {

	
	/**
	 * 
	 * Instances of the JdbcTemplate class are threadsafe once configured. This is important because 
	 * it means that you can configure a single instance of a JdbcTemplate and then safely inject 
	 * this shared reference into multiple DAOs (or repositories). The JdbcTemplate is stateful, 
	 * in that it maintains a reference to a DataSource, but this state is not conversational state.

	 * 
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;

	
    @Scheduled(fixedRate = 15000, initialDelay = 5000)
    public void create() {
    	
    	
		Random r = new Random();
		double randomNumber = 0.99 + r.nextDouble() * (1.1 - 0.99);
    	
        String updateSql = "UPDATE ratings SET averageRating = averageRating*" + randomNumber;
    	jdbcTemplate.update(updateSql);

    }
}