package com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalApiResponseDto {
    @JsonProperty("forecast")
    private Forecast forecast;
    @JsonProperty("location")
    private Location location;
}
