package com.joaobarbosadev.boletrix.api.user.services;

import com.joaobarbosadev.boletrix.api.auth.dto.LoginRequest;
import com.joaobarbosadev.boletrix.api.auth.dto.LoginResponse;
import com.joaobarbosadev.boletrix.api.user.dtos.RegisterRequest;

public interface UserService {

    LoginResponse registerAndLogin(RegisterRequest registerRequest);
}
