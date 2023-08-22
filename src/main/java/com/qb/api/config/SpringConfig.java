package com.qb.api.config;

import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootConfiguration
@OpenAPIDefinition(info = @Info(title = "Businnes API", version = "2.0", description = "Business Application" ) )
public class SpringConfig {
	
	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper().registerModules(new JavaTimeModule(), new Jdk8Module())
				.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
				.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
				.configure(Feature.IGNORE_UNKNOWN, true).setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate template= Mockito.mock(RestTemplate.class);
		return template;

	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
			}
		};
	}
	
	
}
