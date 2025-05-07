package com.joaobarbosadev.boletrix.api.auth.services;

import com.joaobarbosadev.boletrix.api.auth.dto.LoginRequest;
import com.joaobarbosadev.boletrix.api.auth.dto.LoginResponse;
import com.joaobarbosadev.boletrix.api.auth.dto.RefreshRequest;
import com.joaobarbosadev.boletrix.core.models.auth.UserDetailsImpl;
import com.joaobarbosadev.boletrix.core.repository.UserSystemRepository;
import com.joaobarbosadev.boletrix.core.service.token.interfaces.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserSystemRepository userSystemRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, TokenService tokenService, UserSystemRepository userSystemRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userSystemRepository = userSystemRepository;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var userNamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        var authentication = authenticationManager.authenticate(userNamePasswordAuthenticationToken);

        var user = ((UserDetailsImpl) authentication.getPrincipal());
        LoginResponse response = new LoginResponse();
        response.setToken( tokenService.generatedAccessToken(user));
        response.setRefreshToken( tokenService.generateRefreshToken(user));
        return response;
    }

    @Override
    public LoginResponse refreshToken(RefreshRequest refreshRequest) {
        return null;
    }

    @Override
    public void logout(String token, RefreshRequest refreshRequest) {

    }
}
