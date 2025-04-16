package com.celonis.microservices.infrastructure.adapter.out.db;

import com.celonis.microservices.application.port.WeatherDbPort;
import com.celonis.microservices.domain.WeatherForecast;
import com.celonis.microservices.infrastructure.adapter.out.db.mapper.DbMapper;
import com.celonis.microservices.infrastructure.adapter.out.db.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WeatherDbAdapter implements WeatherDbPort {
    private final WeatherRepository repository;
    private final DbMapper mapper;

    @Override
    @Transactional
    public List<WeatherForecast> saveForecast(List<WeatherForecast> weatherForecasts) {
        var weatherEntities = mapper.toEntityList(weatherForecasts);
        if (weatherEntities.isEmpty()) {
            return List.of();
        }
        return mapper.toDomainList(repository.saveAll(weatherEntities));
    }

    @Override
    public List<WeatherForecast> getAllForecasts() {
        return mapper.toDomainList(repository.findAll(Sort.by(Sort.Order.asc("city"), Sort.Order.asc("updatedDate"))));
    }

    @Override
    public List<WeatherForecast> getForecastsByCity(String city) {
        return mapper.toDomainList(repository.findByCityAndUpdatedDateIn(city, List.of(LocalDate.now(), LocalDate.now().plusDays(1))));
    }

    @Override
    public Optional<WeatherForecast> getForecastByCityAndDate(String city, LocalDate date) {
        return Optional.ofNullable(mapper.toDomain(repository.findByCityAndUpdatedDate(city, date).orElse(null)));
    }
}
