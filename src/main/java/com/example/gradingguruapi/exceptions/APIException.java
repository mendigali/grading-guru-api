package com.example.gradingguruapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class APIException extends Exception {
    private final String timestamp;
    private final HttpStatus httpStatus;

    protected APIException(String message, HttpStatus httpStatus) {
        super(message);
        this.timestamp = LocalDateTime.now().toString();
        this.httpStatus = httpStatus;
    }
}
