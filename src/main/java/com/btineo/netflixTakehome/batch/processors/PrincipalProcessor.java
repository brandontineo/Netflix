package com.btineo.netflixTakehome.batch.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.btineo.netflixTakehome.constants.IMBDConstants;
import com.btineo.netflixTakehome.dto.PrincipalDTO;

public class PrincipalProcessor implements ItemProcessor<PrincipalDTO, PrincipalDTO> {
	
    private static final Logger log = LoggerFactory.getLogger(PrincipalProcessor.class);

    @Override
    public PrincipalDTO process(final PrincipalDTO originalDTO) throws Exception {
    	
    	String job = originalDTO.getJob();
    	if (job.contains(IMBDConstants.EMPTY_SYMBOL)) {
    		originalDTO.setJob(IMBDConstants.UNKNOWN);
    	}

        return originalDTO;
    }

  

}