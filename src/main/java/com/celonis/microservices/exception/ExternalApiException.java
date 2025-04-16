package com.celonis.microservices.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExternalApiException extends CoreException {

    public ExternalApiException(ErrorDetail errorDetail, Map<String, String> invalidParams) {
        super(errorDetail, invalidParams);
    }

    public ExternalApiException(Map<String, String> invalidParams) {
        this(ErrorDetail.EXTERNAL_WEATHER_API_500, invalidParams);
    }
}
