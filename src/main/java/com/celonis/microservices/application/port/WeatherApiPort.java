package com.celonis.microservices.application.port;

import com.celonis.microservices.domain.WeatherForecast;

import java.util.List;

public interface WeatherApiPort {
    List<WeatherForecast> fetchWeatherForecast(String city);
}
