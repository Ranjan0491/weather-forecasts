package com.celonis.microservices.weather.api.application.usecase;


import com.celonis.microservices.weather.api.domain.weather.WeatherForecast;

import java.util.List;

public interface WeatherDataUseCase {
    List<WeatherForecast> saveWeatherForecast(String city);
    List<WeatherForecast> getWeatherForeCasts();
    List<WeatherForecast> getWeatherForeCastsByCity(String city);
}
