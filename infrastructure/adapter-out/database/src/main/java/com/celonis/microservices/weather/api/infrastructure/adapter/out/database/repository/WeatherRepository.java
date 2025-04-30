package com.celonis.microservices.weather.api.infrastructure.adapter.out.database.repository;

import com.celonis.microservices.weather.api.infrastructure.adapter.out.database.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    Optional<WeatherEntity> findByCityAndUpdatedDate(String city, LocalDate updateDate);
    List<WeatherEntity> findByCityAndUpdatedDateIn(String city, List<LocalDate> dates);
}

