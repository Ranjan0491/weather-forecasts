package com.celonis.microservices.weather.api.application.usecase.impl;

import com.celonis.microservices.weather.api.application.port.WeatherApiPort;
import com.celonis.microservices.weather.api.application.port.WeatherDbPort;
import com.celonis.microservices.weather.api.domain.weather.WeatherForecast;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherDataUseCaseImplTest {
    @Mock
    private WeatherApiPort weatherApiPort;
    @Mock
    private WeatherDbPort weatherDbPort;
    @InjectMocks
    private WeatherDataUseCaseImpl weatherDataUseCase;

    @Test
    @DisplayName("Test for successful save of weather forecasts")
    void saveWeatherForecast() {
        //given
        var city = "Madrid";
        var weatherForecastList = List.of(
                WeatherForecast.builder()
                        .city("Madrid")
                        .maxTemperatureCentigrade(24.50)
                        .minTemperatureCentigrade(12.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(10)
                        .updatedDate(LocalDate.now())
                        .condition("Sunny")
                        .build(),
                WeatherForecast.builder()
                        .city("Berlin")
                        .maxTemperatureCentigrade(14.50)
                        .minTemperatureCentigrade(2.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(20)
                        .updatedDate(LocalDate.now())
                        .condition("Partly Cloudy")
                        .build()
        );

        //when
        when(weatherApiPort.fetchWeatherForecast(anyString())).thenReturn(weatherForecastList);
        when(weatherDbPort.saveForecast(anyList())).thenReturn(weatherForecastList);

        //then
        var savedForecasts = weatherDataUseCase.saveWeatherForecast(city);
        assertNotNull(savedForecasts);
        assertEquals(2, savedForecasts.size());
        verify(weatherApiPort).fetchWeatherForecast(city);
        verify(weatherDbPort).saveForecast(weatherForecastList);
    }

    @Test
    @DisplayName("Test for successful fetch of all weather forecasts")
    void getWeatherForeCasts() {
        //given
        var weatherForecastList = List.of(
                WeatherForecast.builder()
                        .city("Madrid")
                        .maxTemperatureCentigrade(24.50)
                        .minTemperatureCentigrade(12.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(10)
                        .updatedDate(LocalDate.now())
                        .condition("Sunny")
                        .build(),
                WeatherForecast.builder()
                        .city("Berlin")
                        .maxTemperatureCentigrade(14.50)
                        .minTemperatureCentigrade(2.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(20)
                        .updatedDate(LocalDate.now())
                        .condition("Partly Cloudy")
                        .build()
        );

        //when
        when(weatherDbPort.getAllForecasts()).thenReturn(weatherForecastList);

        //then
        var fetchedForecasts = weatherDataUseCase.getWeatherForeCasts();
        assertNotNull(fetchedForecasts);
        assertEquals(2, fetchedForecasts.size());
        verify(weatherDbPort).getAllForecasts();
    }

    @Test
    @DisplayName("Test for successful fetch of weather forecasts by city")
    void getWeatherForeCastsByCity() {
        //given
        var city = "Madrid";
        var weatherForecastList = List.of(
                WeatherForecast.builder()
                        .city("Madrid")
                        .maxTemperatureCentigrade(24.50)
                        .minTemperatureCentigrade(12.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(10)
                        .updatedDate(LocalDate.now())
                        .condition("Sunny")
                        .build(),
                WeatherForecast.builder()
                        .city("Madrid")
                        .maxTemperatureCentigrade(14.50)
                        .minTemperatureCentigrade(2.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(20)
                        .updatedDate(LocalDate.now().plusDays(1))
                        .condition("Partly Cloudy")
                        .build()
        );

        //when
        when(weatherDbPort.getForecastsByCity(anyString())).thenReturn(weatherForecastList);

        //then
        var savedForecasts = weatherDataUseCase.getWeatherForeCastsByCity(city);
        assertNotNull(savedForecasts);
        assertEquals(2, savedForecasts.size());
        verify(weatherDbPort).getForecastsByCity(city);
    }
}