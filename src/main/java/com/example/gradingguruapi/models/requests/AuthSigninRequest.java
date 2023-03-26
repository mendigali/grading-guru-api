package com.example.gradingguruapi.models.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthSigninRequest {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;
}
