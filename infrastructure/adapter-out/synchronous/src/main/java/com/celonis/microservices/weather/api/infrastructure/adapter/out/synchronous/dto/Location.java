package com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @JsonProperty("name")
    private String name;
}
