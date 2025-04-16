package com.celonis.microservices.infrastructure.adapter.in.controller;

import com.celonis.microservices.application.usecase.WeatherDataUseCase;
import com.celonis.microservices.infrastructure.adapter.in.controller.mapper.DtoMapper;
import com.celonis.openapi.api.WeatherForecastsApi;
import com.celonis.openapi.model.WeatherForecastResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class WeatherController implements WeatherForecastsApi {
    private final WeatherDataUseCase weatherDataUseCase;
    private final DtoMapper mapper;

    @Override
    public ResponseEntity<List<WeatherForecastResponseDto>> saveForecastByCity(@Validated String city) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDtoList(weatherDataUseCase.saveWeatherForecast(city)));
    }

    @Override
    public ResponseEntity<List<WeatherForecastResponseDto>> getAllForecasts() {
        return ResponseEntity.ok(mapper.toDtoList(weatherDataUseCase.getWeatherForeCasts()));
    }

    @Override
    public ResponseEntity<List<WeatherForecastResponseDto>> getForecastsByCity(String city) {
        return ResponseEntity.ok(mapper.toDtoList(weatherDataUseCase.getWeatherForeCastsByCity(city)));
    }
}
