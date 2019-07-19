package com.btineo.netflixTakehome;

import java.util.concurrent.Executor;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;



@EnableAutoConfiguration
@EnableScheduling
@EnableBatchProcessing
@EnableAsync
@Configuration
@ComponentScan(basePackages = {"com.btineo.netflixTakehome"})
public class Application {
	

    @Bean(name="processExecutor")
    public Executor processExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Asynchronous Process-");
        executor.initialize();
        return executor;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
