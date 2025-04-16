package com.celonis.microservices.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ForecastNotFoundException extends CoreException {

    public ForecastNotFoundException(ErrorDetail errorDetail, Map<String, String> invalidParams) {
        super(errorDetail, invalidParams);
    }

    public ForecastNotFoundException() {
        this(ErrorDetail.WEATHER_FORECAST_NOT_FOUND, null);
    }
}
