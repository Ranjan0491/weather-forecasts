package com.celonis.microservices.weather.api.infrastructure.adapter.out.database.mapper;

import com.celonis.microservices.weather.api.domain.weather.WeatherForecast;
import com.celonis.microservices.weather.api.infrastructure.adapter.out.database.entity.WeatherEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("DB Mapper tests")
class DbMapperTest {

    @InjectMocks
    private DbMapper mapper = new DbMapperImpl();

    @Test
    void toDomainList() {
        // given
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

        // when
        var domainList = mapper.toDomainList(entityList);

        // then
        for(var index=0; index<domainList.size(); index++) {
            var weatherEntity = entityList.get(index);
            var weatherForecast = domainList.get(index);
            assertAll(
                    () -> assertEquals(weatherEntity.getCity(), weatherForecast.getCity()),
                    () -> assertEquals(weatherEntity.getCondition(), weatherForecast.getCondition()),
                    () -> assertEquals(weatherEntity.getUpdatedDate(), weatherForecast.getUpdatedDate()),
                    () -> assertEquals(weatherEntity.getAverageHumidity(), weatherForecast.getAverageHumidity()),
                    () -> assertEquals(weatherEntity.getMinTemperatureCentigrade(), weatherForecast.getMinTemperatureCentigrade()),
                    () -> assertEquals(weatherEntity.getMaxTemperatureCentigrade(), weatherForecast.getMaxTemperatureCentigrade()),
                    () -> assertEquals(weatherEntity.getTotalPrecipitationMilliMeter(), weatherForecast.getTotalPrecipitationMilliMeter())
            );
        }
    }

    @Test
    void toEntityList() {
        // given
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

        // when
        var entityList = mapper.toEntityList(domainList);

        // then
        for(var index=0; index<entityList.size(); index++) {
            var weatherForecastDomain = domainList.get(index);
            var weatherEntity = entityList.get(index);
            assertAll(
                    () -> assertEquals(weatherForecastDomain.getCity(), weatherEntity.getCity()),
                    () -> assertEquals(weatherForecastDomain.getCondition(), weatherEntity.getCondition()),
                    () -> assertEquals(weatherForecastDomain.getUpdatedDate(), weatherEntity.getUpdatedDate()),
                    () -> assertEquals(weatherForecastDomain.getAverageHumidity(), weatherEntity.getAverageHumidity()),
                    () -> assertEquals(weatherForecastDomain.getMinTemperatureCentigrade(), weatherEntity.getMinTemperatureCentigrade()),
                    () -> assertEquals(weatherForecastDomain.getMaxTemperatureCentigrade(), weatherEntity.getMaxTemperatureCentigrade()),
                    () -> assertEquals(weatherForecastDomain.getTotalPrecipitationMilliMeter(), weatherEntity.getTotalPrecipitationMilliMeter())
            );
        }
    }
}