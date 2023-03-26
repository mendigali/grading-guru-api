package com.example.gradingguruapi.services;

import com.example.gradingguruapi.dao.entities.Company;
import com.example.gradingguruapi.dao.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public List<Company> getAllByUserId(String userId) {
        return companyRepository.findAllByUserId(userId);
    }
}
