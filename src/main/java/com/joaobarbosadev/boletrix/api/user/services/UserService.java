package com.joaobarbosadev.boletrix.api.user.services;

import com.joaobarbosadev.boletrix.api.auth.dto.LoginRequest;
import com.joaobarbosadev.boletrix.api.auth.dto.LoginResponse;
import com.joaobarbosadev.boletrix.api.user.dtos.RegisterRequest;
import com.joaobarbosadev.boletrix.api.user.dtos.RegisterSystemRequest;
import com.joaobarbosadev.boletrix.api.user.dtos.UserRequest;
import com.joaobarbosadev.boletrix.api.user.dtos.UserResponse;

import java.util.List;

public interface UserService {

    LoginResponse registerAndLogin(RegisterRequest registerRequest);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse editUser(UserRequest request, Long id);
    void deleteUserById(Long id);

    UserResponse register(RegisterSystemRequest registerRequest);
}
