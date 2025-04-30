package com.celonis.microservices.weather.api.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.celonis.microservices.weather.api.infrastructure.adapter.in.controller",
		"com.celonis.openapi.api", // If needed for OpenAPI controllers
		"com.celonis.microservices.weather.api.application",
		"com.celonis.microservices.weather.api.domain",
		"com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous",
		"com.celonis.microservices.weather.api.infrastructure.adapter.out.database"
})
public class WeatherApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApiApplication.class, args);
	}

}
