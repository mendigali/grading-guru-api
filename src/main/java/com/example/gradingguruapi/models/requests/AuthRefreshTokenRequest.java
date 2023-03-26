package com.example.gradingguruapi.models.requests;

import lombok.Data;

@Data
public class AuthRefreshTokenRequest {

    private String refreshToken;
}
