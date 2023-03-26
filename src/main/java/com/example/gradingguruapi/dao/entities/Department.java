package com.example.gradingguruapi.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Department {

    private String id;
    private String name;
    private String companyId;
}
