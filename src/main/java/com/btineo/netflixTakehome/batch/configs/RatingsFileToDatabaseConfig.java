package com.btineo.netflixTakehome.batch.configs;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.btineo.netflixTakehome.batch.IMBDTaskExecutor;
import com.btineo.netflixTakehome.batch.ItemSkipPolicy;
import com.btineo.netflixTakehome.batch.JobCompletionNotificationListener;
import com.btineo.netflixTakehome.batch.processors.RatingsProcessor;
import com.btineo.netflixTakehome.constants.IMBDConstants;
import com.btineo.netflixTakehome.dto.RatingsDTO;

/**
 * Adapted from https://github.com/michaelcgood/Spring-Batch-CSV-Example
 * @author btineo
 *
 */
@EnableBatchProcessing
@Configuration
public class RatingsFileToDatabaseConfig {
	
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
    
    // begin reader, writer, and processor

    
    // https://stackoverflow.com/questions/42270806/using-flatfileitemreader-with-a-taskexecutor-thread-safety
    // It is safe to use a FlatFileItemReader with a TaskExecutor provided the Writer is thread-safe.
    @Bean
    public FlatFileItemReader<RatingsDTO> tsvRatingsReader(){
        FlatFileItemReader<RatingsDTO> reader = new FlatFileItemReader<RatingsDTO>();
        reader.setResource(new ClassPathResource(IMBDConstants.RATINGS_FILE));

        reader.setLineMapper(new DefaultLineMapper<RatingsDTO>() {{
        	
        	
            DelimitedLineTokenizer tabDelimeter = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
            String[] names = new String[] { "tconst", "averageRating", "numVotes" };
            tabDelimeter.setNames(names);
            
        	this.setLineTokenizer(tabDelimeter);

            setFieldSetMapper(new BeanWrapperFieldSetMapper<RatingsDTO>() {{
                setTargetType(RatingsDTO.class);
            }});
        }});
        
        // 
        reader.setSaveState(false);
        
        return reader;
    }


	@Bean
	ItemProcessor<RatingsDTO, RatingsDTO> tsvRatingsProcessor() {
		return new RatingsProcessor();
	}

	// https://docs.spring.io/spring-batch/trunk/apidocs/org/springframework/batch/item/database/JdbcBatchItemWriter.html
	// The writer is thread-safe after its properties are set (normal singleton behavior), so it can be used to write in multiple concurrent transactions.
	@Bean
	public JdbcBatchItemWriter<RatingsDTO> tsvRatingsWriter() {
		 JdbcBatchItemWriter<RatingsDTO> tsvRatingsWriter = new JdbcBatchItemWriter<RatingsDTO>();
		 tsvRatingsWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<RatingsDTO>());
		 

		 tsvRatingsWriter.setSql("INSERT INTO ratings (tconst, averageRating, numVotes) "
		 		+ "VALUES (:tconst, :averageRating, :numVotes)");
		 
		 tsvRatingsWriter.setDataSource(dataSource);
	        return tsvRatingsWriter;
	}
	

	 // end reader, writer, and processor
    // begin job info
	@Bean
	public Step ratingsTsvFileToDatabaseStep() {
		return stepBuilderFactory.get("ratingsTsvFileToDatabaseStep")
				.<RatingsDTO, RatingsDTO>chunk(10000)
				.reader(tsvRatingsReader())
				.processor(tsvRatingsProcessor())
				.writer(tsvRatingsWriter())
                .faultTolerant()
                .skipPolicy(new ItemSkipPolicy())
                .skip(RuntimeException.class) /// TODO: Remove and skip granular logic in ItemSkipPolicy
                // .listener(skipListener) // we could add some logic to print titles that have bad number of columns
                .taskExecutor(IMBDTaskExecutor.taskExecutor()).build();
	}
	


	@Bean("Job3")
	Job ratingsTsvFileToDatabaseJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("ratingsTsvFileToDatabaseJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(ratingsTsvFileToDatabaseStep())
				.end()
				.build();
	}
	 // end job info
}

