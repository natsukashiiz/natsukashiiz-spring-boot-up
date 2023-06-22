package com.natsukashiiz.iiserverapi.controller;

import com.natsukashiiz.iiserverapi.model.request.LoginRequest;
import com.natsukashiiz.iiserverapi.model.request.RegisterRequest;
import com.natsukashiiz.iiserverapi.model.request.TokenRefreshRequest;
import com.natsukashiiz.iiserverapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/auth")
public class AuthApi {
    private final UserService service;

    public AuthApi(UserService service) {
        this.service = service;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest request) {
        return service.create(request);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> login(HttpServletRequest httpRequest, @RequestBody LoginRequest request) {
        return service.login(request, httpRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequest request) {
        return service.refreshToken(request);
    }

}
