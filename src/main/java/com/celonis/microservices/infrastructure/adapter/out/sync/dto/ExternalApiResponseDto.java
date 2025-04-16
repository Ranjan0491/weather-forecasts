package com.celonis.microservices.infrastructure.adapter.out.sync.dto;

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
