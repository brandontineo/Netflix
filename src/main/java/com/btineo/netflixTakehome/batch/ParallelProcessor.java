package com.btineo.netflixTakehome.batch;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
@EnableScheduling
@EnableAsync
public class ParallelProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParallelProcessor.class);

	
	@Autowired
	@Qualifier("Job1")
	Job tsvTitlesFileToDatabaseJob;
	
	@Autowired
	@Qualifier("Job2")
	Job episodeTsvFileToDatabaseJob;
	
	@Autowired
	@Qualifier("Job3")
	Job ratingsTsvFileToDatabaseJob;

	@Autowired
	@Qualifier("Job4")
	Job principalFileToDatabaseJob;
	
	@Autowired
	private JobLauncher jobLauncherTwo;
	

   @Scheduled(fixedRate = 900000000 )
   public void run1(){
       Map<String, JobParameter> confMap = new HashMap<>();
       confMap.put("time", new JobParameter(System.currentTimeMillis()));
       JobParameters jobParameters = new JobParameters(confMap);
       try {
    	   jobLauncherTwo.run(tsvTitlesFileToDatabaseJob, jobParameters);
       }catch (Exception ex){
    	   LOGGER.error(ex.getMessage());
       }

   }

   @Scheduled(fixedRate = 900000000)
   public void run2(){
       Map<String, JobParameter> confMap = new HashMap<>();
       confMap.put("time", new JobParameter(System.currentTimeMillis()));
       JobParameters jobParameters = new JobParameters(confMap);
       try {
    	   jobLauncherTwo.run(episodeTsvFileToDatabaseJob, jobParameters);
       }catch (Exception ex){
    	   LOGGER.error(ex.getMessage());
       }
   }
   
   @Scheduled(fixedRate = 900000000)
   public void run3(){
       Map<String, JobParameter> confMap = new HashMap<>();
       confMap.put("time", new JobParameter(System.currentTimeMillis()));
       JobParameters jobParameters = new JobParameters(confMap);
       try {
    	   jobLauncherTwo.run(ratingsTsvFileToDatabaseJob, jobParameters);
       }catch (Exception ex){
    	   LOGGER.error(ex.getMessage());
       }
   }
   
   @Scheduled(fixedRate = 900000000)
   public void run4(){
       Map<String, JobParameter> confMap = new HashMap<>();
       confMap.put("time", new JobParameter(System.currentTimeMillis()));
       JobParameters jobParameters = new JobParameters(confMap);
       try {
    	   jobLauncherTwo.run(principalFileToDatabaseJob, jobParameters);
       }catch (Exception ex){
    	   LOGGER.error(ex.getMessage());
       }
   }

}