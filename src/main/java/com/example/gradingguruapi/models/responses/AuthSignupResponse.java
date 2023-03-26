package com.example.gradingguruapi.models.responses;

import com.example.gradingguruapi.dao.entities.Company;
import com.example.gradingguruapi.dao.entities.Department;
import com.example.gradingguruapi.dao.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthSignupResponse {

    private String accessToken;
    private String refreshToken;
    private User user;
    private Company company;
    private Department department;
}
