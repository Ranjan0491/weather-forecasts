package com.celonis.microservices.weather.api.infrastructure.adapter.out.database;

import com.celonis.microservices.weather.api.domain.weather.WeatherForecast;
import com.celonis.microservices.weather.api.infrastructure.adapter.out.database.entity.WeatherEntity;
import com.celonis.microservices.weather.api.infrastructure.adapter.out.database.mapper.DbMapper;
import com.celonis.microservices.weather.api.infrastructure.adapter.out.database.mapper.DbMapperImpl;
import com.celonis.microservices.weather.api.infrastructure.adapter.out.database.repository.WeatherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherDbAdapterTest {
    @Mock
    private WeatherRepository repository;
    @Spy
    private DbMapper mapper = new DbMapperImpl();
    @InjectMocks
    private WeatherDbAdapter weatherDbAdapter;

    @Test
    @DisplayName("Test for saving weather forecast data")
    void saveForecast() {
        //given
        var entityList = List.of(
                WeatherEntity.builder()
                        .city("Madrid")
                        .maxTemperatureCentigrade(24.50)
                        .minTemperatureCentigrade(12.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(10)
                        .updatedDate(LocalDate.now())
                        .condition("Sunny")
                        .build(),
                WeatherEntity.builder()
                        .city("Berlin")
                        .maxTemperatureCentigrade(14.50)
                        .minTemperatureCentigrade(2.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(20)
                        .updatedDate(LocalDate.now())
                        .condition("Partly Cloudy")
                        .build()
        );
        var domainList = List.of(
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
        when(repository.saveAll(anyList())).thenReturn(entityList);

        //then
        var savedList = weatherDbAdapter.saveForecast(domainList);
        assertNotNull(savedList);
        assertEquals(2, savedList.size());
        verify(repository).saveAll(entityList);
    }

    @Test
    @DisplayName("Test for saving empty weather forecast data")
    void saveForecast2() {
        //given
        var domainList = new ArrayList<WeatherForecast>();

        //then
        var savedList = weatherDbAdapter.saveForecast(domainList);
        assertNotNull(savedList);
        assertTrue(savedList.isEmpty());
    }

    @Test
    @DisplayName("Test for fetching all saved data from DB")
    void getAllForecasts() {
        //given
        var sort = Sort.by(Sort.Order.asc("city"), Sort.Order.asc("updatedDate"));
        var entityList = List.of(
                WeatherEntity.builder()
                        .city("Madrid")
                        .maxTemperatureCentigrade(24.50)
                        .minTemperatureCentigrade(12.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(10)
                        .updatedDate(LocalDate.now())
                        .condition("Sunny")
                        .build(),
                WeatherEntity.builder()
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
        when(repository.findAll(any(Sort.class))).thenReturn(entityList);

        //then
        var fetchedData = weatherDbAdapter.getAllForecasts();
        assertNotNull(fetchedData);
        assertEquals(2, fetchedData.size());
        verify(repository).findAll(sort);
    }

    @Test
    @DisplayName("Fetch weather forecast by city")
    void getForecastsByCity() {
        //given
        var city = "Madrid";
        var entityList = List.of(
                WeatherEntity.builder()
                        .city("Madrid")
                        .maxTemperatureCentigrade(24.50)
                        .minTemperatureCentigrade(12.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(10)
                        .updatedDate(LocalDate.now())
                        .condition("Sunny")
                        .build(),
                WeatherEntity.builder()
                        .city("Madrid")
                        .maxTemperatureCentigrade(14.50)
                        .minTemperatureCentigrade(2.40)
                        .totalPrecipitationMilliMeter(0.0)
                        .averageHumidity(20)
                        .updatedDate(LocalDate.now())
                        .condition("Partly Cloudy")
                        .build()
        );
        //when
        when(repository.findByCityAndUpdatedDateIn(anyString(), anyList())).thenReturn(entityList);

        //then
        var fetchedData = weatherDbAdapter.getForecastsByCity(city);
        assertNotNull(fetchedData);
        assertEquals(2, fetchedData.size());
        verify(repository).findByCityAndUpdatedDateIn(city, List.of(LocalDate.now(), LocalDate.now().plusDays(1)));
    }

    @Test
    @DisplayName("Fetch weather forecast by city")
    void getForecastByCityAndDate() {
        //given
        var city = "Madrid";
        var date = LocalDate.now();
        var weatherEntity = WeatherEntity.builder()
                .city("Madrid")
                .maxTemperatureCentigrade(24.50)
                .minTemperatureCentigrade(12.40)
                .totalPrecipitationMilliMeter(0.0)
                .averageHumidity(10)
                .updatedDate(LocalDate.now())
                .condition("Sunny")
                .build();
        var optionalEntity = Optional.ofNullable(weatherEntity);

        //when
        when(repository.findByCityAndUpdatedDate(anyString(), any(LocalDate.class))).thenReturn(optionalEntity);

        //then
        var fetchedData = weatherDbAdapter.getForecastByCityAndDate(city, date);
        assertTrue(fetchedData.isPresent());
        verify(repository).findByCityAndUpdatedDate(city, date);
    }
}