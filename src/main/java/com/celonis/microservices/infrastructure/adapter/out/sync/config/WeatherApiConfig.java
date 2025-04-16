package com.celonis.microservices.infrastructure.adapter.out.sync.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WeatherApiConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, RestTemplateErrorHandler restTemplateErrorHandler) {
        return restTemplateBuilder.errorHandler(restTemplateErrorHandler).build();
    }
}
