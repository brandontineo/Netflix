package com.btineo.netflixTakehome.batch.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.btineo.netflixTakehome.dto.EpisodesDTO;

public class EpisodesProcessor implements ItemProcessor<EpisodesDTO, EpisodesDTO> {
	
    private static final Logger log = LoggerFactory.getLogger(EpisodesProcessor.class);
    

    @Override
    public EpisodesDTO process(final EpisodesDTO originalDTO) throws Exception {
    	
        return originalDTO;
    }

  

}