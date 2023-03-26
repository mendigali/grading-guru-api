package com.example.gradingguruapi.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Company {

    private String id;
    private String name;
    private String description;
    private String inviteCode;
}
