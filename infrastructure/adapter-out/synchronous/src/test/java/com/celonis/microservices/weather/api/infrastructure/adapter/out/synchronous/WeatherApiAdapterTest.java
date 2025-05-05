package com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous;

import com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous.dto.*;
import com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous.mapper.ExternalApiResponseMapper;
import com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous.mapper.ExternalApiResponseMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WeatherApiAdapterTest {
    @Mock
    private RestTemplate restTemplate;
    @Spy
    private final ExternalApiResponseMapper mapper = new ExternalApiResponseMapperImpl();
    @InjectMocks
    private WeatherApiAdapter adapter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(adapter, "url", "http://localhost:1000/v1/forecast.json");
        ReflectionTestUtils.setField(adapter, "apiKey", "some_api_key");
    }

    @Test
    @DisplayName("Test for external API call")
    void fetchWeatherForecast() {
        //given
        var city = "Madrid";
        var apiKey = "some_api_key";
        var url = "http://localhost:1000/v1/forecast.json";
        var externalApiResponseDto = ExternalApiResponseDto.builder()
                .location(Location.builder().name(city).build())
                .forecast(Forecast.builder()
                        .forecastDay(List.of(
                                ForecastDay.builder()
                                        .date(LocalDate.now())
                                        .day(Day.builder()
                                                .averageHumidity(50)
                                                .minTemperatureCentigrade(13.50)
                                                .maxTemperatureCentigrade(24.40)
                                                .totalPrecipitationMilliMeter(13.00)
                                                .condition(Condition.builder().text("Rainy").build())
                                                .build())
                                        .build(),
                                ForecastDay.builder()
                                        .date(LocalDate.now().plusDays(1))
                                        .day(Day.builder()
                                                .averageHumidity(50)
                                                .minTemperatureCentigrade(13.50)
                                                .maxTemperatureCentigrade(24.40)
                                                .totalPrecipitationMilliMeter(13.00)
                                                .condition(Condition.builder().text("Rainy").build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .build();

        //when
        when(restTemplate.getForEntity(eq(UriComponentsBuilder.fromUriString(url)
                        .queryParam("q", city)
                        .queryParam("key", apiKey)
                        .queryParam("days", 2)
                        .build()
                        .toUri()), eq(ExternalApiResponseDto.class)))
                .thenReturn(ResponseEntity.ok(externalApiResponseDto));

        //then
        var domainList = adapter.fetchWeatherForecast(city);
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