package com.celonis.microservices.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherForecast {
    private String city;
    private LocalDate updatedDate;
    private Double maxTemperatureCentigrade;
    private Double minTemperatureCentigrade;
    private Double totalPrecipitationMilliMeter;
    private Integer averageHumidity;
    private String condition;
}
