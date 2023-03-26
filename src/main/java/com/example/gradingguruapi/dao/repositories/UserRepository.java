package com.example.gradingguruapi.dao.repositories;

import com.example.gradingguruapi.dao.entities.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(@Qualifier("databaseJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) {
        String query = "INSERT INTO users (id, username, password, full_name, years_of_experience, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(query, user.getId(), user.getUsername(), user.getPassword(), user.getFullName(), user.getYearsOfExperience(), user.getDateOfBirth());
    }

    public User findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ? LIMIT 1";

        return new ArrayList<>(jdbcTemplate.query(query, new Object[]{username}, (rs, rowNum) ->
                new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getInt("years_of_experience"),
                        rs.getString("date_of_birth")
                )
        )).get(0);
    }

    public Boolean existsByUsername(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{username}, Integer.class) > 0;
    }

    public Boolean existsByEmail(String email) {
        return null;
    }
}
