package com.glebestraikh.crmsystem.util;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}

