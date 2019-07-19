package com.btineo.netflixTakehome.batch.configs;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
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
import com.btineo.netflixTakehome.batch.processors.EpisodesProcessor;
import com.btineo.netflixTakehome.constants.IMBDConstants;
import com.btineo.netflixTakehome.dto.EpisodesDTO;
import com.btineo.netflixTakehome.dto.JobCompletionNotificationListener;

/**
 * Adapted from https://github.com/michaelcgood/Spring-Batch-CSV-Example
 * @author btineo
 *
 */
@EnableBatchProcessing
@Configuration
public class EpisodesFileToDatabaseConfig {
	
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
    
	
	@Autowired
	public JobLauncher jobLauncherTwo;
	
    
    // begin reader, writer, and processor

    
    // https://stackoverflow.com/questions/42270806/using-flatfileitemreader-with-a-taskexecutor-thread-safety
    // It is safe to use a FlatFileItemReader with a TaskExecutor provided the Writer is thread-safe.
    @Bean
    public FlatFileItemReader<EpisodesDTO> tsvEpisodesReader(){
        FlatFileItemReader<EpisodesDTO> reader = new FlatFileItemReader<EpisodesDTO>();
        
        reader.setResource(new ClassPathResource(IMBDConstants.EPSIODES_FILE));
        reader.setLineMapper(new DefaultLineMapper<EpisodesDTO>() {{
        	
        	
            DelimitedLineTokenizer tabDelimeter = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
            String[] names = new String[] { "tconst", "parentTconst", "seasonNumber", "episodeNumber" };
            tabDelimeter.setNames(names);
            
        	this.setLineTokenizer(tabDelimeter);

            setFieldSetMapper(new BeanWrapperFieldSetMapper<EpisodesDTO>() {{
                setTargetType(EpisodesDTO.class);
            }});
        }});
        
        // 
        reader.setSaveState(false);
        
        return reader;
    }


	@Bean
	ItemProcessor<EpisodesDTO, EpisodesDTO> tsvEpisodesProcessor() {
		return new EpisodesProcessor();
	}

	// https://docs.spring.io/spring-batch/trunk/apidocs/org/springframework/batch/item/database/JdbcBatchItemWriter.html
	// The writer is thread-safe after its properties are set (normal singleton behavior), so it can be used to write in multiple concurrent transactions.
	@Bean
	public JdbcBatchItemWriter<EpisodesDTO> tsvEpisodesWriter() {
		 JdbcBatchItemWriter<EpisodesDTO> tsvTitleWriter = new JdbcBatchItemWriter<EpisodesDTO>();
		 tsvTitleWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EpisodesDTO>());
		 

		 tsvTitleWriter.setSql("INSERT INTO episodes (tconst, parentTconst, seasonNumber, episodeNumber) "
		 		+ "VALUES (:tconst, :parentTconst, :seasonNumber, :episodeNumber)");
		 
		 tsvTitleWriter.setDataSource(dataSource);
	        return tsvTitleWriter;
	}
	

	 // end reader, writer, and processor
    // begin job info
	@Bean
	public Step episodeTsvFileToDatabaseStep() {
		return stepBuilderFactory.get("episodeTsvFileToDatabaseStep")
				.<EpisodesDTO, EpisodesDTO>chunk(10000)
				.reader(tsvEpisodesReader())
				.processor(tsvEpisodesProcessor())
				.writer(tsvEpisodesWriter())
                .faultTolerant()
                .skipPolicy(new ItemSkipPolicy())
                .skip(RuntimeException.class) /// TODO: Remove and skip granular logic in ItemSkipPolicy
                // .listener(skipListener) // we could add some logic to print titles that have bad number of columns
                .taskExecutor(IMBDTaskExecutor.taskExecutor()).build();
	}
	


	@Bean("Job2")
	Job episodeTsvFileToDatabaseJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("episodeTsvFileToDatabaseJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(episodeTsvFileToDatabaseStep())
				.end()
				.build();
	}
	 // end job info
}

