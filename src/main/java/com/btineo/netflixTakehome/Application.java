package com.btineo.netflixTakehome;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = {"com.btineo.netflixTakehome"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	

    @PostConstruct
    private void init() {
        System.out.println("InitDemoApplication initialization logic ...");
        // ...
    }

}
