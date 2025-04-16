package com.celonis.microservices.infrastructure.adapter.out.sync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Day {
    @JsonProperty("maxtemp_c")
    private Double maxTemperatureCentigrade;
    @JsonProperty("mintemp_c")
    private Double minTemperatureCentigrade;
    @JsonProperty("totalprecip_mm")
    private Double totalPrecipitationMilliMeter;
    @JsonProperty("avghumidity")
    private Integer averageHumidity;
    @JsonProperty("condition")
    private Condition condition;
}
