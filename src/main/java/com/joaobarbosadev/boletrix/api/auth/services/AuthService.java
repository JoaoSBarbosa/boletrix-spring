package com.joaobarbosadev.boletrix.api.auth.services;

import com.joaobarbosadev.boletrix.api.auth.dto.LoginRequest;
import com.joaobarbosadev.boletrix.api.auth.dto.LoginResponse;
import com.joaobarbosadev.boletrix.api.auth.dto.RefreshRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refreshToken(RefreshRequest refreshRequest);
    void logout(String token, RefreshRequest refreshRequest);
}
