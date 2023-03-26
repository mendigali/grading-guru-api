package com.example.gradingguruapi.services;

import com.example.gradingguruapi.dao.entities.Company;
import com.example.gradingguruapi.dao.entities.Department;
import com.example.gradingguruapi.dao.entities.RefreshToken;
import com.example.gradingguruapi.dao.entities.User;
import com.example.gradingguruapi.dao.repositories.CompanyRepository;
import com.example.gradingguruapi.dao.repositories.DepartmentRepository;
import com.example.gradingguruapi.dao.repositories.UserRepository;
import com.example.gradingguruapi.exceptions.APIBadRequestException;
import com.example.gradingguruapi.exceptions.APITokenExpiredException;
import com.example.gradingguruapi.jwt.JwtUtils;
import com.example.gradingguruapi.models.enums.UserRolesEnum;
import com.example.gradingguruapi.models.requests.AuthRefreshTokenRequest;
import com.example.gradingguruapi.models.requests.AuthSigninRequest;
import com.example.gradingguruapi.models.requests.AuthSignupRequest;
import com.example.gradingguruapi.models.responses.AuthRefreshTokenResponse;
import com.example.gradingguruapi.models.responses.AuthSigninResponse;
import com.example.gradingguruapi.models.responses.AuthSignupResponse;
import com.example.gradingguruapi.models.responses.NullResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public AuthSigninResponse signin(AuthSigninRequest request) throws APIBadRequestException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new APIBadRequestException("Error: User doesn't exist!");
        }

        User user = userRepository.findByUsername(request.getUsername());

        if (!user.getPassword().equals(request.getPassword())) {
            throw new APIBadRequestException("Error: Password is incorrect!");
        }

        String accessToken = jwtUtils.generateAccessTokenFromUsername(user.getUsername());
        String refreshToken = refreshTokenService.save(user.getId()).getToken();

        return new AuthSigninResponse(
                accessToken,
                refreshToken,
                user
        );
    }

    public AuthSignupResponse signup(AuthSignupRequest request) throws APIBadRequestException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new APIBadRequestException("Error: Username is already taken!");
        }

        User user = new User(
                UUID.randomUUID().toString(),
                request.getUsername(),
                request.getPassword(),
                request.getFullName() != null ? request.getFullName() : "Пользователь " + request.getUsername(),
                request.getYearsOfExperience() != null ? request.getYearsOfExperience() : 0,
                request.getDateOfBirth() != null ? request.getDateOfBirth() : "01.01.2000"
        );

        userRepository.save(user);

        Company company = new Company(
                UUID.randomUUID().toString(),
                "Компания " + request.getUsername(),
                "Описание компании " + request.getUsername(),
                UUID.randomUUID().toString()
        );
        companyRepository.save(company, user.getId(), UserRolesEnum.ROLE_COMPANY_ADMIN);

        Department department = new Department(
                UUID.randomUUID().toString(),
                "Отдел " + request.getUsername(),
                company.getId()
        );

        departmentRepository.save(department, user.getId(), UserRolesEnum.ROLE_DEPARTMENT_ADMIN);

        String accessToken = jwtUtils.generateAccessTokenFromUsername(user.getUsername());
        String refreshToken = refreshTokenService.save(user.getId()).getToken();

        return new AuthSignupResponse(accessToken, refreshToken, user, company, department);
    }

    public NullResponse signout() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        refreshTokenService.deleteByUserId(userId);

        return new NullResponse();
    }

    public AuthRefreshTokenResponse refreshToken(AuthRefreshTokenRequest request) throws APIBadRequestException, APITokenExpiredException {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken());

        if (refreshToken == null) {
            throw new APIBadRequestException("Refresh token is not in database!");
        }

        refreshTokenService.verifyExpiration(refreshToken);

        refreshToken = refreshTokenService.save(refreshToken.getUserId());

        String accessToken = jwtUtils.generateAccessTokenFromUsername(refreshToken.getUserId());

        return new AuthRefreshTokenResponse(accessToken, refreshToken.getToken());
    }
}
