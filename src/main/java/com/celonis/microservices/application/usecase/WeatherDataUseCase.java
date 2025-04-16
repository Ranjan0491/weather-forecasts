package com.celonis.microservices.application.usecase;

import com.celonis.microservices.domain.WeatherForecast;

import java.util.List;

public interface WeatherDataUseCase {
    List<WeatherForecast> saveWeatherForecast(String city);
    List<WeatherForecast> getWeatherForeCasts();
    List<WeatherForecast> getWeatherForeCastsByCity(String city);
}
