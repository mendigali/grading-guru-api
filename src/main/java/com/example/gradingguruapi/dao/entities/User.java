package com.example.gradingguruapi.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String id;
    private String username;
    private String password;
    private String fullName;
    private int yearsOfExperience;
    private String dateOfBirth;
}
