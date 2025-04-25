package com.celonis.microservices.exception;

import com.celonis.openapi.model.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String applicationName;

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(CityNotFoundException cityNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildAndPrintErrorResponseDto(cityNotFoundException.getCode(),
                        cityNotFoundException.getMessage(), cityNotFoundException.getInvalidParams()));
    }

    @ExceptionHandler(ForecastNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(ForecastNotFoundException forecastNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildAndPrintErrorResponseDto(forecastNotFoundException.getCode(),
                        forecastNotFoundException.getMessage(), forecastNotFoundException.getInvalidParams()));
    }

    @ExceptionHandler(BadKeyConfigurationException.class)
    public ResponseEntity<ErrorResponseDto> handleException(BadKeyConfigurationException badKeyConfigurationException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildAndPrintErrorResponseDto(badKeyConfigurationException.getCode(),
                        badKeyConfigurationException.getMessage(), badKeyConfigurationException.getInvalidParams()));
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ErrorResponseDto> handleException(ExternalApiException externalApiException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildAndPrintErrorResponseDto(externalApiException.getCode(),
                        externalApiException.getMessage(), externalApiException.getInvalidParams()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleException(ConstraintViolationException constraintViolationException) {
        var constraintViolations = constraintViolationException.getConstraintViolations();
        var invalidParams = constraintViolations.stream()
                .collect(Collectors.toMap(
                        constraintViolation -> constraintViolation.getPropertyPath().toString(),
                        constraintViolation -> String.valueOf(constraintViolation.getInvalidValue())
                ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildAndPrintErrorResponseDto("CONSTRAINT_400",
                        constraintViolationException.getMessage(), invalidParams));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildAndPrintErrorResponseDto("WEATHER_500", exception.getMessage()));
    }

    private ErrorResponseDto buildAndPrintErrorResponseDto(String code, String message, Map<String, String> invalidParams) {
        var errorResponseDto = ErrorResponseDto.builder()
                .application(applicationName)
                .code(code)
                .message(message)
                .invalidParams(invalidParams)
                .build();
        log.error("Handled error :: " + errorResponseDto);
        return errorResponseDto;
    }

    private ErrorResponseDto buildAndPrintErrorResponseDto(String code, String message) {
        return buildAndPrintErrorResponseDto(code, message, null);
    }
}
