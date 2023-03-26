package com.example.gradingguruapi.dao.repositories;

import com.example.gradingguruapi.dao.entities.Company;
import com.example.gradingguruapi.dao.entities.User;
import com.example.gradingguruapi.models.enums.UserRolesEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    private final JdbcTemplate jdbcTemplate;

    public CompanyRepository(@Qualifier("databaseJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Company company, String userId, UserRolesEnum role) {
        String query = "INSERT INTO companies (id, name, description, invite_code) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(query, company.getId(), company.getName(), company.getDescription(), company.getInviteCode());

        String query2 = "INSERT INTO user_company (user_id, company_id, role_name) VALUES (?, ?, ?)";

        jdbcTemplate.update(query2, userId, company.getId(), role.toString());
    }

    public List<Company> findAllByUserId(String userId) {
        String query = "SELECT * " +
                "FROM user_company " +
                "JOIN companies on user_company.company_id = companies.id " +
                "WHERE user_company.user_id = ?";

        return new ArrayList<>(jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) ->
                new Company(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("invite_code")
                )
        ));
    }
}
