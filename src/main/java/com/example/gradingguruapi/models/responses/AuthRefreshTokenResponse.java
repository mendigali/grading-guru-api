package com.example.gradingguruapi.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRefreshTokenResponse {

    private String accessToken;
    private String refreshToken;
}
