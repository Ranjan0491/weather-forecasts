package com.celonis.microservices.weather.api.domain.exception;

import java.util.Map;

public class BadKeyConfigurationException extends CoreException {

    public BadKeyConfigurationException(ErrorDetail errorDetail, Map<String, String> invalidParams) {
        super(errorDetail, invalidParams);
    }

    public BadKeyConfigurationException(Map<String, String> invalidParams) {
        this(ErrorDetail.BAD_API_KEY, invalidParams);
    }
}
