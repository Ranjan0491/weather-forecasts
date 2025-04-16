package com.celonis.microservices.infrastructure.adapter.in.controller.mapper;

import com.celonis.microservices.domain.WeatherForecast;
import com.celonis.openapi.model.WeatherForecastResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DtoMapper {
    List<WeatherForecastResponseDto> toDtoList(List<WeatherForecast> weatherForecasts);
}
