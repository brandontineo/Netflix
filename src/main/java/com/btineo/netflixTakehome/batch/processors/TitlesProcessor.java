package com.btineo.netflixTakehome.batch.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.btineo.netflixTakehome.constants.IMBDConstants;
import com.btineo.netflixTakehome.dto.TitlesDTO;

public class TitlesProcessor implements ItemProcessor<TitlesDTO, TitlesDTO> {
	
    private static final Logger log = LoggerFactory.getLogger(TitlesProcessor.class);
    
    

    
    @Override
    public TitlesDTO process(final TitlesDTO originalDTO) throws Exception {
    	
        // Here we could apply some sort of logic if desired to change the data in TitlesDTO
    	// But for now we just pass the title data back as is.
    	TitlesDTO titleToStore = new TitlesDTO();
    	titleToStore.setPrimaryTitle(originalDTO.getPrimaryTitle());
    	titleToStore.setStartYear(originalDTO.getStartYear());
    	
    	if (originalDTO.getEndYear().contains(IMBDConstants.EMPTY_SYMBOL)) {
        	titleToStore.setEndYear(originalDTO.getStartYear());
        	log.debug("End year was invalid or missing so assuming same as start year");
    	} else {
        	titleToStore.setEndYear(originalDTO.getEndYear());
    	}
    	
    	if (!checkIfValidEntry(titleToStore.getStartYear(), originalDTO.getTitleType())) {
    		return null;
    	}
    	
    	titleToStore.setTconst(originalDTO.getTconst());
    	titleToStore.setTitleType(originalDTO.getTitleType());


        return titleToStore;
    }

    /**
     * Checks if this record should be stored based on the year and type
     * @param startYear
     * @param titleType
     */
	private boolean checkIfValidEntry(String startYear, String titleType) {
		
		
		int parsedStartYear = Integer.parseInt(startYear);
		if (parsedStartYear != IMBDConstants.START_YEAR_OF_INTEREST || 
				!IMBDConstants.TYPES_OF_INTEREST.contains(titleType)) {
			
			log.debug("Skipping this data point from year outside of bounds");
			return false;			
		}
		
		return true;

	}

}