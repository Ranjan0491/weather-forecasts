package com.celonis.microservices.exception;

import lombok.Getter;

@Getter
public enum ErrorDetail {
    CITY_NOT_FOUND("CITY_404", "Provided city could not be found"),
    WEATHER_FORECAST_NOT_FOUND("FORECAST_404", "Weather forecast could not be found for provided city and date"),
    BAD_API_KEY("KEY_403", "Provided API key is either invalid or expired"),
    EXTERNAL_WEATHER_API_500("EXT_WEATHER_500", "Received error from external API")
    ;

    private String code;
    private String message;

    ErrorDetail(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
