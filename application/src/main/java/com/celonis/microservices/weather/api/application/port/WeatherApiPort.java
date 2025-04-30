package com.celonis.microservices.weather.api.application.port;


import com.celonis.microservices.weather.api.domain.weather.WeatherForecast;

import java.util.List;

public interface WeatherApiPort {
    List<WeatherForecast> fetchWeatherForecast(String city);
}
