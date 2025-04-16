package com.celonis.microservices.infrastructure.adapter.out.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "weather_data")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "city")
    private String city;
    @Column(name = "updated_Date")
    private LocalDate updatedDate;
    @Column(name = "max_temp_c")
    private Double maxTemperatureCentigrade;
    @Column(name = "min_temp_c")
    private Double minTemperatureCentigrade;
    @Column(name = "total_preci_mm")
    private Double totalPrecipitationMilliMeter;
    @Column(name = "avg_humidity")
    private Integer averageHumidity;
    @Column(name = "weather_condition")
    private String condition;
}
