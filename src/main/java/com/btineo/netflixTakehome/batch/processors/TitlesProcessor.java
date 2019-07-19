package com.btineo.netflixTakehome.batch.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.btineo.netflixTakehome.dto.TitlesDTO;

public class TitlesProcessor implements ItemProcessor<TitlesDTO, TitlesDTO> {
	
    private static final Logger log = LoggerFactory.getLogger(TitlesProcessor.class);
    
    private static final String MISSING_SYMBOL = "\\N";
    private static final int START_YEAR_OF_INTEREST = 2018;
    private static final int END_YEAR_OF_INTEREST = 2018;
    private static final String TYPE_OF_INTEREST = "movie";

    
    @Override
    public TitlesDTO process(final TitlesDTO originalDTO) throws Exception {
    	
        // Here we could apply some sort of logic if desired to change the data in TitlesDTO
    	// But for now we just pass the title data back as is.
    	TitlesDTO titleToStore = new TitlesDTO();
    	titleToStore.setPrimaryTitle(originalDTO.getPrimaryTitle());
    	titleToStore.setStartYear(originalDTO.getStartYear());
    	
    	if (originalDTO.getEndYear().contains(MISSING_SYMBOL)) {
        	titleToStore.setEndYear(originalDTO.getStartYear());
        	log.debug("End year was invalid or missing so assuming same as start year");
    	} else {
        	titleToStore.setEndYear(originalDTO.getEndYear());
    	}
    	
    	if (!checkIfValidEntry(titleToStore.getStartYear(), titleToStore.getEndYear(), originalDTO.getTitleType())) {
    		return null;
    	}
    	
    	titleToStore.setTconst(originalDTO.getTconst());

        return titleToStore;
    }

    /**
     * Checks if this record should be stored based on the year and type
     * @param startYear
     * @param endYear
     * @param titleType
     */
	private boolean checkIfValidEntry(String startYear, String endYear, String titleType) {
		
		
		int parsedStartYear = Integer.parseInt(startYear);
		int parsedEndYear = Integer.parseInt(endYear);

		if (parsedStartYear != START_YEAR_OF_INTEREST || parsedEndYear != END_YEAR_OF_INTEREST
				|| !titleType.equals(TYPE_OF_INTEREST)) {
			
			log.debug("Skipping this data point from year outside of bounds");
			return false;			
		}
		
		return true;

	}

}