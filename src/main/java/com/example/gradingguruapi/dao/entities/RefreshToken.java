package com.example.gradingguruapi.dao.entities;

import lombok.Data;

@Data
public class RefreshToken {

    private String id;
    private String token;
    private String userId;
    private String expiryDate;
}
