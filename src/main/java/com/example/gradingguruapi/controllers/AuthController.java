package com.example.gradingguruapi.controllers;

import com.example.gradingguruapi.exceptions.APIBadRequestException;
import com.example.gradingguruapi.exceptions.APITokenExpiredException;
import com.example.gradingguruapi.models.requests.AuthRefreshTokenRequest;
import com.example.gradingguruapi.models.requests.AuthSigninRequest;
import com.example.gradingguruapi.models.requests.AuthSignupRequest;
import com.example.gradingguruapi.models.responses.AuthRefreshTokenResponse;
import com.example.gradingguruapi.models.responses.AuthSigninResponse;
import com.example.gradingguruapi.models.responses.AuthSignupResponse;
import com.example.gradingguruapi.models.responses.NullResponse;
import com.example.gradingguruapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public AuthSigninResponse authenticateUser(@Valid @RequestBody AuthSigninRequest request) throws APIBadRequestException {
        return authService.signin(request);
    }

    @PostMapping("/signup")
    public AuthSignupResponse registerUser(@Valid @RequestBody AuthSignupRequest request) throws APIBadRequestException {
        return authService.signup(request);
    }

    @PostMapping("/signout")
    public NullResponse logoutUser() {
        return authService.signout();
    }

    @PostMapping("/refreshtoken")
    public AuthRefreshTokenResponse refreshToken(@Valid @RequestBody AuthRefreshTokenRequest request) throws APITokenExpiredException, APIBadRequestException {
        return authService.refreshToken(request);
    }

}
