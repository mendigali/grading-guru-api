package com.example.gradingguruapi.dao.repositories;

import com.example.gradingguruapi.dao.entities.RefreshToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    public RefreshTokenRepository(@Qualifier("databaseJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(RefreshToken refreshToken) {
        String query = "INSERT INTO refresh_tokens (id, token, user_id, expiry_date) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(query, refreshToken.getId(), refreshToken.getToken(), refreshToken.getUserId(), refreshToken.getExpiryDate());
    }

    public RefreshToken findByToken(String token) {
        return null;
    }

    public void delete(RefreshToken refreshToken) {
    }

    public int deleteByUserGuid(String userId) {
        return 0;
    }
}
