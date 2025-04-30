package com.celonis.microservices.weather.api.infrastructure.adapter.out.synchronous.config;

import com.celonis.microservices.weather.api.domain.exception.BadKeyConfigurationException;
import com.celonis.microservices.weather.api.domain.exception.CityNotFoundException;
import com.celonis.microservices.weather.api.domain.exception.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Component
@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() ||
                response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        log.error("Error occurred while invoking {} operation of {} url with status code {} : {}", method.name(), url.toString(), response.getStatusCode(), response);
        if(response.getStatusCode().is4xxClientError()) {
            if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CityNotFoundException(Map.of("queryParameterCity", "Not a valid city"));
            } else if(response.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new BadKeyConfigurationException(Map.of("queryParameterKey", "Not a valid API key"));
            }
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new ExternalApiException(Map.of("externalApiException", response.toString()));
        }
    }
}
