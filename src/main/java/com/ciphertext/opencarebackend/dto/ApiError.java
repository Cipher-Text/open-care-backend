package com.ciphertext.opencarebackend.dto;

import java.time.LocalDateTime;

public class ApiError {
    private int status;
    private String title;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(int status, String title, String message) {
        this.status = status;
        this.title = title;
        this.message = message;
    }
}
