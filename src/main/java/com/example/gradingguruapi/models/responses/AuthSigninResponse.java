package com.example.gradingguruapi.models.responses;

import com.example.gradingguruapi.dao.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthSigninResponse {

    private String accessToken;
    private String refreshToken;
    private User user;
}
