package com.celonis.microservices.weather.api.it;

import com.celonis.openapi.model.WeatherForecastResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("it-tests")
@DisplayName("Integration Tests")
public class WeatherControllerIT extends IntegrationTestUtils {

    @Nested
    @DisplayName("Tests for saving weather data")
    class TestPostEndpoint {
        @Test
        @DisplayName("Save weather data for today and tomorrow")
        void saveWeatherIT() throws Exception {
            // given
            var city = "Madrid";
            var date = LocalDate.parse("2025-04-08");
            var datePlusOne = date.plusDays(1);
            mockExternalApi(city);

            // when
            var weatherDtoList = createCommonWeatherForecast(city);

            // then
            assertNotNull(weatherDtoList);
            assertFalse(weatherDtoList.isEmpty());
            var weatherEntityList = List.of(weatherRepository.findByCityAndUpdatedDate(city, date).get(), weatherRepository.findByCityAndUpdatedDate(city, datePlusOne).get());
            for(var index=0; index<weatherDtoList.size(); index++) {
                var weatherDto = weatherDtoList.get(index);
                var weatherEntity = weatherEntityList.get(index);
                assertNotNull(weatherEntity);
                assertAll(
                        () -> assertEquals(weatherDto.getCity(), weatherEntity.getCity()),
                        () -> assertEquals(weatherDto.getUpdatedDate(), weatherEntity.getUpdatedDate()),
                        () -> assertEquals(weatherDto.getCondition(), weatherEntity.getCondition()),
                        () -> assertEquals(weatherDto.getAverageHumidity(), weatherEntity.getAverageHumidity()),
                        () -> assertEquals(weatherDto.getMinTemperatureCentigrade().doubleValue(), weatherEntity.getMinTemperatureCentigrade()),
                        () -> assertEquals(weatherDto.getMaxTemperatureCentigrade().doubleValue(), weatherEntity.getMaxTemperatureCentigrade()),
                        () -> assertEquals(weatherDto.getTotalPrecipitationMilliMeter().doubleValue(), weatherEntity.getTotalPrecipitationMilliMeter())
                );
            }
        }
    }

    private List<WeatherForecastResponseDto> createCommonWeatherForecast(String city) throws Exception {
        // when
        var response = mockMvc.perform(
                        post("/v1/weather-forecasts")
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("city", city)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(response, new TypeReference<List<WeatherForecastResponseDto>>(){});
    }
}
