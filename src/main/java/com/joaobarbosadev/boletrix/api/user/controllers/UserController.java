package com.joaobarbosadev.boletrix.api.user.controllers;

import com.joaobarbosadev.boletrix.api.auth.dto.LoginResponse;
import com.joaobarbosadev.boletrix.api.user.dtos.*;
import com.joaobarbosadev.boletrix.api.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> registerAndLogin(
            @RequestBody RegisterRequest registerRequest
    ) {
        LoginResponse response = userService.registerAndLogin(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/system_register")
    public ResponseEntity<UserResponse> register(
            @RequestBody RegisterSystemRequest registerRequest
    ) {
        UserResponse response = userService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        return ResponseEntity.ok(userService.editUser(userRequest, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PutMapping("/teste")
    public ResponseEntity<String> test() {
        System.out.println("TESTE REALIZADO");
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<LoginResponse> updateProfile(
            @RequestBody UserUpdate userUpdate,
            @PathVariable Long id) {

        System.out.println("CHEGOU NA API: "+ userUpdate);

        LoginResponse response = userService.updateProfile(userUpdate, id);
        return ResponseEntity.ok(response);

    }

    @PutMapping("/profile/teste/{id}")
    public ResponseEntity<String> updateProfileTeste(
            @RequestBody UserUpdate userUpdate,
            @PathVariable Long id) {

        System.out.println("CHEGOU NA API COM: "+ userUpdate);

        return ResponseEntity.ok("FUNCIOU");

    }


}
