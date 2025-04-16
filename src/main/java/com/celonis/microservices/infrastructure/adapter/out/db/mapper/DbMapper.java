package com.celonis.microservices.infrastructure.adapter.out.db.mapper;

import com.celonis.microservices.domain.WeatherForecast;
import com.celonis.microservices.infrastructure.adapter.out.db.entity.WeatherEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DbMapper {
    List<WeatherForecast> toDomainList(List<WeatherEntity> weatherEntities);
    List<WeatherEntity> toEntityList(List<WeatherForecast> weatherForecasts);
    WeatherForecast toDomain(WeatherEntity weatherEntity);
}
