package com.celonis.microservices.application.port;

import com.celonis.microservices.domain.WeatherForecast;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeatherDbPort {
    List<WeatherForecast> saveForecast(List<WeatherForecast> weatherForecasts);
    List<WeatherForecast> getAllForecasts();
    List<WeatherForecast> getForecastsByCity(String city);

    Optional<WeatherForecast> getForecastByCityAndDate(String city, LocalDate date);
}
