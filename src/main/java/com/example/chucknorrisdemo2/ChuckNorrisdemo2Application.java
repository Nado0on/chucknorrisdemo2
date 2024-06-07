package com.example.chucknorrisdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



@SpringBootApplication
public class ChuckNorrisdemo2Application {

	private static final Logger log = LoggerFactory.getLogger(ChuckNorrisdemo2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(ChuckNorrisdemo2Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> {
			Joke quote = restTemplate.getForObject(
					"https://api.chucknorris.io/jokes/random", Joke.class);
			log.info(quote.toString());
		}, 0, 10, TimeUnit.SECONDS);
		return args -> {
		};
	}

}
