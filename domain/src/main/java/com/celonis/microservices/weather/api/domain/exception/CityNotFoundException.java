package com.celonis.microservices.weather.api.domain.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CityNotFoundException extends CoreException {

    public CityNotFoundException(ErrorDetail errorDetail, Map<String, String> invalidParams) {
        super(errorDetail, invalidParams);
    }

    public CityNotFoundException(Map<String, String> invalidParams) {
        this(ErrorDetail.CITY_NOT_FOUND, invalidParams);
    }
}
