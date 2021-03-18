package com.jacaranda.diettogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.jacaranda.restcontroller", "com.jacaranda.services",
		"com.jacaranda.services.impl", "com.jacaranda.model.dto", "com.jacaranda.security",
		"com.jacaranda.security.services", "com.jacaranda.security.services.impl", "com.jacaranda.security.model.dto",
		"com.jacaranda.security.model", "com.jacaranda.security.common", "com.jacaranda.security.filter", "com.jacaranda.security.filter.jwt" })
@EntityScan(basePackages = { "com.jacaranda.model", "com.jacaranda.security.model" })
@EnableJpaRepositories(basePackages = { "com.jacaranda.repository", "com.jacaranda.security.repository" })
public class DietTogetherApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DietTogetherApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DietTogetherApplication.class);
    }

}
