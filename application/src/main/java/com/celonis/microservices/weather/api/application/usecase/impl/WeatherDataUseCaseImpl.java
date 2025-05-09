package com.celonis.microservices.weather.api.application.usecase.impl;

import com.celonis.microservices.weather.api.application.port.WeatherApiPort;
import com.celonis.microservices.weather.api.application.port.WeatherDbPort;
import com.celonis.microservices.weather.api.application.usecase.WeatherDataUseCase;
import com.celonis.microservices.weather.api.domain.weather.WeatherForecast;
import com.celonis.microservices.weather.api.domain.exception.ForecastNotFoundException;
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
                .map(weatherForecast -> {
                    var forecastFromDb = weatherDbPort.getForecastByCityAndDate(city, weatherForecast.getUpdatedDate());
                    if(forecastFromDb.isEmpty()) {
                        return weatherForecast;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
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
