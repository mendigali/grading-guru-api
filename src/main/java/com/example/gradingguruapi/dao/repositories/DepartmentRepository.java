package com.example.gradingguruapi.dao.repositories;

import com.example.gradingguruapi.dao.entities.Department;
import com.example.gradingguruapi.models.enums.UserRolesEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(@Qualifier("databaseJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Department department, String userId, UserRolesEnum role) {
        String query = "INSERT INTO departments (id, name, company_id) VALUES (?, ?, ?)";

        jdbcTemplate.update(query, department.getId(), department.getName(), department.getCompanyId());

        String query2 = "INSERT INTO user_department (user_id, department_id, role_name) VALUES (?, ?, ?)";

        jdbcTemplate.update(query2, userId, department.getId(), role.toString());
    }

    public List<Department> findAllByCompanyId(String companyId) {
        String query = "SELECT * FROM departments WHERE company_id = ?";

        return jdbcTemplate.query(query, new Object[]{companyId}, (rs, rowNum) ->
                new Department(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("company_id")
                )
        );
    }
}
