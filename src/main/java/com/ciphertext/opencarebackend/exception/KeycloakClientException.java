package com.ciphertext.opencarebackend.exception;

public class KeycloakClientException extends RuntimeException {
    public KeycloakClientException(String message) {
        super(message);
    }
}
