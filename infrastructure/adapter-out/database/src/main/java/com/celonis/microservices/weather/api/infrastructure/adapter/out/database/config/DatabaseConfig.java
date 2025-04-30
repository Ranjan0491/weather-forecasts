package com.celonis.microservices.weather.api.infrastructure.adapter.out.database.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.celonis.microservices.weather.api.infrastructure.adapter.out.database.repository")
@EntityScan(basePackages = {
        "com.celonis.microservices.weather.api.infrastructure.adapter.out.database.entity"
})
public class DatabaseConfig {
}
