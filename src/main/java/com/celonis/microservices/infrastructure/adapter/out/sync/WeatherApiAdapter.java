package com.celonis.microservices.infrastructure.adapter.out.sync;

import com.celonis.microservices.application.port.WeatherApiPort;
import com.celonis.microservices.domain.WeatherForecast;
import com.celonis.microservices.infrastructure.adapter.out.sync.dto.ExternalApiResponseDto;
import com.celonis.microservices.infrastructure.adapter.out.sync.mapper.ExternalApiResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherApiAdapter implements WeatherApiPort {
    private final RestTemplate restTemplate;
    private final ExternalApiResponseMapper mapper;

    @Value("${app.weather-api.url}")
    private String url;
    @Value("${app.weather-api.api-key}")
    private String apiKey;

    @Override
    public List<WeatherForecast> fetchWeatherForecast(String city) {
        var externalApiResponseDto = restTemplate
                .getForEntity(UriComponentsBuilder.fromUriString(url)
                        .queryParam("q", city)
                        .queryParam("key", apiKey)
                        .queryParam("days", 2)
                        .build()
                        .toUri(), ExternalApiResponseDto.class)
                .getBody();

        log.info("externalApiResponseDto: {}", externalApiResponseDto);
        return mapper.toDomainList(externalApiResponseDto);
    }
}
