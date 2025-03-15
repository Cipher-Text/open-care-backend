package com.ciphertext.opencarebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Add error handler
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return response.getStatusCode().is4xxClientError() ||
                        response.getStatusCode().is5xxServerError();
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getStatusCode().is4xxClientError()) {
                    throw new KeycloakClientException("Keycloak client error: " +
                            response.getStatusCode() + " " + StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
                } else if (response.getStatusCode().is5xxServerError()) {
                    throw new KeycloakServerException("Keycloak server error: " +
                            response.getStatusCode() + " " + StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
                }
            }
        });

        return restTemplate;
    }

    public static class KeycloakClientException extends RuntimeException {
        public KeycloakClientException(String message) {
            super(message);
        }
    }

    public static class KeycloakServerException extends RuntimeException {
        public KeycloakServerException(String message) {
            super(message);
        }
    }
}