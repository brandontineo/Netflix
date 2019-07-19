package com.btineo.netflixTakehome.batch.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.btineo.netflixTakehome.dto.RatingsDTO;

public class RatingsProcessor implements ItemProcessor<RatingsDTO, RatingsDTO> {
	
    private static final Logger log = LoggerFactory.getLogger(RatingsProcessor.class);
    

    @Override
    public RatingsDTO process(final RatingsDTO originalDTO) throws Exception {
    	
        return originalDTO;
    }

  

}