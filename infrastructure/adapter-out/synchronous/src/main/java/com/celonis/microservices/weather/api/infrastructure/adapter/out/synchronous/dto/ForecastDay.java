package com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastDay {
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("day")
    private Day day;
}
