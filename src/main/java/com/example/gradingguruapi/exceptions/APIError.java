package com.example.gradingguruapi.exceptions;

import lombok.Getter;

@Getter
public class APIError {
    private final String message;
    private final String timestamp;

    public APIError(APIException exception) {
        this.message = exception.getMessage();
        this.timestamp = exception.getTimestamp();
    }
}
