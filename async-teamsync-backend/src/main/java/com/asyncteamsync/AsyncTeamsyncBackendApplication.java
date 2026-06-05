package com.asyncteamsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AsyncTeamsyncBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncTeamsyncBackendApplication.class, args);
	}

}
