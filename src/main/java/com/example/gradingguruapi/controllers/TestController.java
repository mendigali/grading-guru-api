package com.example.gradingguruapi.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Hello, everyone!";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_ADMIN') or hasRole('ROLE_DEPARTMENT_ADMIN')")
    public String userAccess() {
        return "Hello, user!";
    }

    @GetMapping("/company-admin")
    @PreAuthorize("hasRole('ROLE_COMPANY_ADMIN')")
    public String moderatorAccess() {
        return "Hello, company admin!";
    }

    @GetMapping("/department-admin")
    @PreAuthorize("hasRole('ROLE_DEPARTMENT_ADMIN')")
    public String adminAccess() {
        return "Hello, department admin!";
    }
}
