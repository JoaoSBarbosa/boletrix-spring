package com.joaobarbosadev.boletrix.api.auth.controllers;

import com.joaobarbosadev.boletrix.api.auth.dto.LoginRequest;
import com.joaobarbosadev.boletrix.api.auth.dto.LoginResponse;
import com.joaobarbosadev.boletrix.api.auth.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
