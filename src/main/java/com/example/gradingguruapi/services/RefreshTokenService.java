package com.example.gradingguruapi.services;

import com.example.gradingguruapi.dao.entities.RefreshToken;
import com.example.gradingguruapi.dao.repositories.RefreshTokenRepository;
import com.example.gradingguruapi.exceptions.APITokenExpiredException;
import com.example.gradingguruapi.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    JwtUtils jwtUtils;

    @Value("${app.jwt.refreshtoken.expires}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken save(String userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setId(UUID.randomUUID().toString());
        refreshToken.setUserId(userId);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs).toString());
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public void verifyExpiration(RefreshToken token) throws APITokenExpiredException {
        if (Instant.parse(token.getExpiryDate()).compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new APITokenExpiredException("Refresh token was expired. Please make a new signin request");
        }
    }

    @Transactional
    public void deleteByUserId(String userId) {
        refreshTokenRepository.deleteByUserGuid(userId);
    }
}
