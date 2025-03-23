package com.ciphertext.opencarebackend.exception;

public class KeycloakClientException extends BadRequestException {
    private static final long serialVersionUID = 1L;

    public KeycloakClientException(String message) {
        super(message);
    }

    public KeycloakClientException(String message, Throwable cause) {
        super(message, cause);
    }
}