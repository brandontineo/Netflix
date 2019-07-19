//package com.btineo.netflixTakehome.batch;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameter;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.launch.support.SimpleJobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.transaction.PlatformTransactionManager;
//
//
//@Configuration
//@EnableBatchProcessing
//@EnableScheduling
//public class JobLauncherConfig {
//	private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherConfig.class);
//
//	
//	@Autowired
//	@Qualifier("Job1")
//	Job csvFileToDatabaseJob;
//
//	@Autowired
//	@Qualifier("Job2")
//	Job tsvTitlesFileToDatabaseJob;
//
//	@Scheduled(fixedDelay = 2000000)
//	public void lanchJobs() throws Exception {
//		Map<String, JobParameter> maps = new HashMap<>();
//        maps.put("time", new JobParameter(System.currentTimeMillis()));
//        JobParameters parameters = new JobParameters(maps);
//
//        try {
//			LOGGER.info("About to launch job....");
//			JobExecution csvFileToDatabaseJobExecution = jobLauncherInit().run(csvFileToDatabaseJob, parameters);
//			LOGGER.info("csvFileToDatabaseJobExecution job execution completed, status : {} " , csvFileToDatabaseJobExecution.getExitStatus());
//
//			JobExecution tsvTitlesFileToDatabaseJobExecution = jobLauncherInit().run(tsvTitlesFileToDatabaseJob, parameters);
//			LOGGER.info("tsvTitlesFileToDatabaseJobExecution job execution completed, status : {} " , tsvTitlesFileToDatabaseJobExecution.getExitStatus());
//
//		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
//				| JobParametersInvalidException e) {
//			LOGGER.error("Error message : {} " ,  e.getMessage());
//		}
//
//	}
//	
//
//    @Bean
//    public DataSource getDataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.h2.Driver");
//        dataSourceBuilder.url("jdbc:h2:mem:test");
//        dataSourceBuilder.username("SA");
//        dataSourceBuilder.password("");
//        return dataSourceBuilder.build();
//    }
//    
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
//        factory.setDataSource(getDataSource());
//        return factory;
//    }
//    
//	@Bean
//	public PlatformTransactionManager transactionManagerGet() {
//		return new HibernateTransactionManager(sessionFactory().getObject());
//	}
//    
//    
//	protected JobRepository createJobRepository() throws Exception {
//	    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//	    factory.setDataSource(getDataSource());
//	   factory.setTransactionManager(transactionManagerGet());
//	    factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
//	    factory.setTablePrefix("BATCH_");
//	    factory.setMaxVarCharLength(1000);
//	    return factory.getObject();
//	}
//	
//	
//	@Bean
//	public JobLauncher jobLauncherInit() throws Exception {
//	        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
//	        jobLauncher.setJobRepository(createJobRepository());
//	        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
//	        jobLauncher.afterPropertiesSet();
//	        return jobLauncher;
//	}
//
//}