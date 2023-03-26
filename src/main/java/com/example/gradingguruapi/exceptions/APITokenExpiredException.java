package com.example.gradingguruapi.exceptions;

import org.springframework.http.HttpStatus;

public class APITokenExpiredException extends APIException {
    public APITokenExpiredException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
