package com.filter.keyword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class KeywordApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeywordApplication.class, args);
	}

}
