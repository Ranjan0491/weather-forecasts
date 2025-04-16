package com.celonis.microservices.application.usecase.impl;

import com.celonis.microservices.application.port.WeatherApiPort;
import com.celonis.microservices.application.port.WeatherDbPort;
import com.celonis.microservices.application.usecase.WeatherDataUseCase;
import com.celonis.microservices.domain.WeatherForecast;
import com.celonis.microservices.exception.ForecastNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class WeatherDataUseCaseImpl implements WeatherDataUseCase {
    private final WeatherApiPort weatherApiPort;
    private final WeatherDbPort weatherDbPort;

    @Override
    public List<WeatherForecast> saveWeatherForecast(String city) {
        var weatherForecasts = weatherApiPort.fetchWeatherForecast(city);
        var forecastsToSave = weatherForecasts.stream()
                .map(weatherForecast -> weatherDbPort.getForecastByCityAndDate(city, weatherForecast.getUpdatedDate()).orElse(weatherForecast))
                .toList();
        return weatherDbPort.saveForecast(forecastsToSave);
    }

    @Override
    public List<WeatherForecast> getWeatherForeCasts() {
        return weatherDbPort.getAllForecasts();
    }

    @Override
    public List<WeatherForecast> getWeatherForeCastsByCity(String city) {
        var forecasts = weatherDbPort.getForecastsByCity(city);
        if(forecasts.isEmpty()) {
            throw new ForecastNotFoundException();
        }

        return forecasts;
    }
}
