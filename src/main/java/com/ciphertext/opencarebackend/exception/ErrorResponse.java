package com.ciphertext.opencarebackend.exception;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {
    private int statusCode;
    private LocalDateTime timestamp;
    private String error;
    private String message;
    private String path;
    private String traceId;
}