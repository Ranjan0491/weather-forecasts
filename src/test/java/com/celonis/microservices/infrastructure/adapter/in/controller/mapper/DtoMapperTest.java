package com.celonis.microservices.infrastructure.adapter.in.controller.mapper;

import com.celonis.microservices.domain.WeatherForecast;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DTO Mapper tests")
class DtoMapperTest {

    @InjectMocks
    private DtoMapper mapper = new DtoMapperImpl();

    @Test
    void toDtoList() {
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
        var dtoList = mapper.toDtoList(domainList);

        // then
        for(var index=0; index<dtoList.size(); index++) {
            var weatherForecastDomain = domainList.get(index);
            var weatherForecastDto = dtoList.get(index);
            assertAll(
                    () -> assertEquals(weatherForecastDomain.getCity(), weatherForecastDto.getCity()),
                    () -> assertEquals(weatherForecastDomain.getCondition(), weatherForecastDto.getCondition()),
                    () -> assertEquals(weatherForecastDomain.getUpdatedDate(), weatherForecastDto.getUpdatedDate()),
                    () -> assertEquals(weatherForecastDomain.getAverageHumidity(), weatherForecastDto.getAverageHumidity()),
                    () -> assertEquals(weatherForecastDomain.getMinTemperatureCentigrade(), weatherForecastDto.getMinTemperatureCentigrade().doubleValue()),
                    () -> assertEquals(weatherForecastDomain.getMaxTemperatureCentigrade(), weatherForecastDto.getMaxTemperatureCentigrade().doubleValue()),
                    () -> assertEquals(weatherForecastDomain.getTotalPrecipitationMilliMeter(), weatherForecastDto.getTotalPrecipitationMilliMeter().doubleValue())
            );
        }
    }
}