package com.celonis.microservices.infrastructure.adapter.out.sync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Forecast {
    @JsonProperty("forecastday")
    private List<ForecastDay> forecastDay;
}
