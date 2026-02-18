package com.monger.ultrastar;

import jakarta.annotation.Nonnull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UltrastartQueueApplication {
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@Nonnull CorsRegistry registry ) {
				registry.addMapping("/**").allowedMethods("DELETE", "GET", "PUT", "POST", "OPTIONS");
			}
		};
	}


	public static void main(String[] args) {
		SpringApplication.run(UltrastartQueueApplication.class, args);
	}
}
