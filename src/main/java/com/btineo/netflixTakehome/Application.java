package com.btineo.netflixTakehome;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;



@EnableAutoConfiguration
@EnableScheduling
@EnableBatchProcessing
@EnableAsync
@Configuration
@ComponentScan(basePackages = {"com.btineo.netflixTakehome"})
public class Application {
	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
