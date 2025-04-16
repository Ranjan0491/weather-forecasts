package com.celonis.microservices.infrastructure.adapter.out.sync.mapper;

import com.celonis.microservices.domain.WeatherForecast;
import com.celonis.microservices.infrastructure.adapter.out.sync.dto.ExternalApiResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExternalApiResponseMapper {

    default List<WeatherForecast> toDomainList(ExternalApiResponseDto externalApiResponseDto) {
        if(externalApiResponseDto == null) {
            return null;
        }
        var city = externalApiResponseDto.getLocation().getName();

        return externalApiResponseDto.getForecast().getForecastDay().stream().map(
                forecastDay -> WeatherForecast.builder()
                        .city(city)
                        .updatedDate(forecastDay.getDate())
                        .maxTemperatureCentigrade(forecastDay.getDay().getMaxTemperatureCentigrade())
                        .minTemperatureCentigrade(forecastDay.getDay().getMinTemperatureCentigrade())
                        .totalPrecipitationMilliMeter(forecastDay.getDay().getTotalPrecipitationMilliMeter())
                        .averageHumidity(forecastDay.getDay().getAverageHumidity())
                        .condition(forecastDay.getDay().getCondition().getText())
                        .build()
        ).toList();
    }
}
