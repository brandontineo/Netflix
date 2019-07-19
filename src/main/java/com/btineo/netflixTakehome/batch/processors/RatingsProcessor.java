package com.btineo.netflixTakehome.batch.processors;


import org.springframework.batch.item.ItemProcessor;

import com.btineo.netflixTakehome.dto.RatingsDTO;

public class RatingsProcessor implements ItemProcessor<RatingsDTO, RatingsDTO> {
	
    

    @Override
    public RatingsDTO process(final RatingsDTO originalDTO) throws Exception {
    	
        return originalDTO;
    }

  

}