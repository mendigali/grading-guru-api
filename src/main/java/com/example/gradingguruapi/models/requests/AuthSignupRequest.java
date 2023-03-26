package com.example.gradingguruapi.models.requests;

import lombok.Data;

@Data
public class AuthSignupRequest {

    private String username;
    private String password;
    private String fullName;
    private String dateOfBirth;
    private Integer yearsOfExperience;
}
