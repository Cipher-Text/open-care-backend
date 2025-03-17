package com.ciphertext.opencarebackend.exception;

public class KeycloakServerException extends RuntimeException {
    public KeycloakServerException(String message) {
        super(message);
    }
}