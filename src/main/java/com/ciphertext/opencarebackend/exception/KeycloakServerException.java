package com.ciphertext.opencarebackend.exception;

public class KeycloakServerException extends BaseException {
    private static final long serialVersionUID = 1L;

    public KeycloakServerException(String message) {
        super(message);
    }

    public KeycloakServerException(String message, Throwable cause) {
        super(message, cause);
    }
}