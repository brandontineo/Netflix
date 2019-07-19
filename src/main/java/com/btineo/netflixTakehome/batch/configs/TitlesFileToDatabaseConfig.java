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
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.btineo.netflixTakehome.batch.IMBDTaskExecutor;
import com.btineo.netflixTakehome.batch.ItemSkipPolicy;
import com.btineo.netflixTakehome.batch.processors.TitlesProcessor;
import com.btineo.netflixTakehome.constants.IMBDConstants;
import com.btineo.netflixTakehome.dto.JobCompletionNotificationListener;
import com.btineo.netflixTakehome.dto.TitlesDTO;

/**
 * Adapted from https://github.com/michaelcgood/Spring-Batch-CSV-Example
 * @author btineo
 *
 */
@EnableBatchProcessing
@Configuration
public class TitlesFileToDatabaseConfig {
	
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
    public FlatFileItemReader<TitlesDTO> tsvTitleReader(){
        FlatFileItemReader<TitlesDTO> reader = new FlatFileItemReader<TitlesDTO>();
        reader.setResource(new ClassPathResource(IMBDConstants.TITLES_FILE));
        reader.setLineMapper(new DefaultLineMapper<TitlesDTO>() {{
        	
        	
            DelimitedLineTokenizer tabDelimeter = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
            String[] names = new String[] { "tconst", "titleType", "primaryTitle", "originalTitle", "isAdult",
            		"startYear", "endYear", "runtimeMinutes", "genres" };
            tabDelimeter.setNames(names);
            
        	this.setLineTokenizer(tabDelimeter);

            setFieldSetMapper(new BeanWrapperFieldSetMapper<TitlesDTO>() {{
                setTargetType(TitlesDTO.class);
            }});
        }});
        
        // 
        reader.setSaveState(false);
        
        return reader;
    }


	@Bean
	ItemProcessor<TitlesDTO, TitlesDTO> tsvTitleProcessor() {
		return new TitlesProcessor();
	}

	// https://docs.spring.io/spring-batch/trunk/apidocs/org/springframework/batch/item/database/JdbcBatchItemWriter.html
	// The writer is thread-safe after its properties are set (normal singleton behavior), so it can be used to write in multiple concurrent transactions.
	@Bean
	public JdbcBatchItemWriter<TitlesDTO> tsvTitleWriter() {
		 JdbcBatchItemWriter<TitlesDTO> tsvTitleWriter = new JdbcBatchItemWriter<TitlesDTO>();
		 tsvTitleWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TitlesDTO>());
		 

		 tsvTitleWriter.setSql("INSERT INTO titles (tconst, titleType, primaryTitle, originalTitle, "
		 		+ "isAdult, startYear, endYear, runtimeMinutes, genres) VALUES ("
		 		+ ":tconst, :titleType, :primaryTitle, :originalTitle, :isAdult, :startYear, :endYear, :runtimeMinutes, :genres"
		 		+ ")");
		 tsvTitleWriter.setDataSource(dataSource);
	        return tsvTitleWriter;
	}
	


	 // end reader, writer, and processor
    // begin job info
	@Bean
	public Step tsvTitlesFileToDatabaseStep() {
		return stepBuilderFactory.get("tsvTitlesFileToDatabaseStep")
				.<TitlesDTO, TitlesDTO>chunk(10000)
				.reader(tsvTitleReader())
				.processor(tsvTitleProcessor())
				.writer(tsvTitleWriter())
                .faultTolerant()
                .skipPolicy(new ItemSkipPolicy())
                .skip(RuntimeException.class)
                // .listener(skipListener) // we could add some logic to print titles that have bad number of columns
                .taskExecutor(IMBDTaskExecutor.taskExecutor()).build();
	}
	

	@Bean("Job1")
	Job tsvTitlesFileToDatabaseJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("tsvTitlesFileToDatabaseJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(tsvTitlesFileToDatabaseStep())
				.end()
				.build();
	}
	 // end job info
}

