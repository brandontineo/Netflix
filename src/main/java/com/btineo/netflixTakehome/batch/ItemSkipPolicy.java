package com.btineo.netflixTakehome.batch;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

/**
 * Skip policy used to skip a row read in if it doesn't have the correct number of columns.
 * 
 * Time permitting this class can be used to specify more granular behavior for when an exception should be ignored.
 * 
 * @author btineo
 *
 */
public class ItemSkipPolicy implements SkipPolicy {
	
	
	public boolean shouldSkip(final Throwable t, final int skipCount) throws SkipLimitExceededException {
	    return true;
	}
}