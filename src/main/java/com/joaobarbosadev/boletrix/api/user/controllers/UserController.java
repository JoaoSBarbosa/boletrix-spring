package com.joaobarbosadev.boletrix.api.user.controllers;

import com.joaobarbosadev.boletrix.api.auth.dto.LoginResponse;
import com.joaobarbosadev.boletrix.api.user.dtos.RegisterRequest;
import com.joaobarbosadev.boletrix.api.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        LoginResponse response = userService.registerAndLogin(registerRequest);
        return ResponseEntity.ok(response);
    }
}
