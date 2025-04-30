package com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous.mapper;

import com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("External API Mapper tests")
class ExternalApiResponseMapperTest {

    @InjectMocks
    private ExternalApiResponseMapper mapper = new ExternalApiResponseMapperImpl();

    @Test
    void toDomainList() {
        // given
        var externalApiResponseDto = ExternalApiResponseDto.builder()
                .location(Location.builder()
                        .name("Madrid")
                        .build())
                .forecast(Forecast.builder()
                        .forecastDay(List.of(
                                ForecastDay.builder()
                                        .date(LocalDate.now())
                                        .day(Day.builder()
                                                .averageHumidity(38)
                                                .maxTemperatureCentigrade(24.30)
                                                .minTemperatureCentigrade(12.00)
                                                .totalPrecipitationMilliMeter(0.00)
                                                .condition(Condition.builder().text("Sunny").build())
                                                .build())
                                        .build(),
                                ForecastDay.builder()
                                        .date(LocalDate.now().plusDays(1))
                                        .day(Day.builder()
                                                .averageHumidity(78)
                                                .maxTemperatureCentigrade(23.30)
                                                .minTemperatureCentigrade(11.00)
                                                .totalPrecipitationMilliMeter(60.00)
                                                .condition(Condition.builder().text("Sunny").build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .build();

        // when
        var domainList = mapper.toDomainList(externalApiResponseDto);

        // then
        assertEquals(externalApiResponseDto.getLocation().getName(), domainList.get(0).getCity());
        var forecastDays =  externalApiResponseDto.getForecast().getForecastDay();
        for(var index=0; index<forecastDays.size(); index++) {
            var forecastDay = forecastDays.get(index);
            var weatherForecast = domainList.get(index);
            assertAll(
                    () -> assertEquals(forecastDay.getDate(), weatherForecast.getUpdatedDate()),
                    () -> assertEquals(forecastDay.getDay().getCondition().getText(), weatherForecast.getCondition()),
                    () -> assertEquals(forecastDay.getDay().getAverageHumidity(), weatherForecast.getAverageHumidity()),
                    () -> assertEquals(forecastDay.getDay().getMinTemperatureCentigrade(), weatherForecast.getMinTemperatureCentigrade()),
                    () -> assertEquals(forecastDay.getDay().getMaxTemperatureCentigrade(), weatherForecast.getMaxTemperatureCentigrade()),
                    () -> assertEquals(forecastDay.getDay().getTotalPrecipitationMilliMeter(), weatherForecast.getTotalPrecipitationMilliMeter())
            );
        }
    }
}