package com.celonis.microservices.weather.api.infrastructure.adapter.out.database.mapper;

import com.celonis.microservices.weather.api.domain.weather.WeatherForecast;
import com.celonis.microservices.weather.api.infrastructure.adapter.out.database.entity.WeatherEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DbMapper {
    List<WeatherForecast> toDomainList(List<WeatherEntity> weatherEntities);
    List<WeatherEntity> toEntityList(List<WeatherForecast> weatherForecasts);
    WeatherForecast toDomain(WeatherEntity weatherEntity);
}
