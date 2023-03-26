package com.example.gradingguruapi.controllers;

import com.example.gradingguruapi.dao.entities.Company;
import com.example.gradingguruapi.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/{userId}")
    public List<Company> getAllByUserId(@PathVariable String userId) {
        return companyService.getAllByUserId(userId);
    }
}
