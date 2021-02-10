package com.jacaranda.diettogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.jacaranda.restcontroller", "com.jacaranda.services",
		"com.jacaranda.services.impl", "com.jacaranda.model.dto", "com.jacaranda.security",
		"com.jacaranda.security.services", "com.jacaranda.security.services.impl", "com.jacaranda.security.model.dto" })
@EntityScan(basePackages = { "com.jacaranda.model", "com.jacaranda.security.model" })
@EnableJpaRepositories(basePackages = { "com.jacaranda.repository", "com.jacaranda.security.repository" })
public class DietTogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietTogetherApplication.class, args);
	}

}
