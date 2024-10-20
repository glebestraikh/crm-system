package com.glebestraikh.crmsystem.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
