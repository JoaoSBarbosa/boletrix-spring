package com.joaobarbosadev.boletrix.api.user.services;

import com.joaobarbosadev.boletrix.api.auth.dto.LoginRequest;
import com.joaobarbosadev.boletrix.api.auth.dto.LoginResponse;
import com.joaobarbosadev.boletrix.api.auth.services.AuthService;
import com.joaobarbosadev.boletrix.api.user.dtos.RegisterRequest;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
import com.joaobarbosadev.boletrix.core.models.domain.UserSystem;
import com.joaobarbosadev.boletrix.core.repository.UserSystemRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserSystemRepository userSystemRepository;
    private final AuthService userAuthService;


    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserSystemRepository userSystemRepository, AuthService userAuthService) {
        this.passwordEncoder = passwordEncoder;
        this.userSystemRepository = userSystemRepository;
        this.userAuthService = userAuthService;
    }

    @Override
    public LoginResponse registerAndLogin(RegisterRequest registerRequest) {
        checkValues(registerRequest);
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        UserSystem userSystem = new UserSystem();
        buildUserSystem(registerRequest,userSystem,encodedPassword);
        userSystem = userSystemRepository.save(userSystem);
        return authenticateUser(userSystem, registerRequest.getPassword());
    }




    private void buildUserSystem(RegisterRequest request,  UserSystem userSystem, String encodedPassword) {
        userSystem.setPassword(encodedPassword);
        userSystem.setEmail(request.getEmail());
        userSystem.setName(request.getName());
    }

    private void checkValues(RegisterRequest registerRequest) {
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new CustomEmptyFieldException("O campo E-mail deve ser preenchido.");
        }
        validatePasswords(registerRequest);
    }

    private void validatePasswords(RegisterRequest registerRequest) {
        String password = registerRequest.getPassword();
        String confirm = registerRequest.getConfirmationPassword();
        if (password == null || confirm == null || password.isBlank() || confirm.isBlank()) {
            throw new CustomEmptyFieldException("A senha e a confirmação de senha devem ser preenchidas.");
        }

        if (!password.equals(confirm)) {
            throw new IllegalArgumentException("A senha e a confirmação de senha não coincidem.");
        }
    }
    private LoginResponse authenticateUser(UserSystem userSystem, String basePassString) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(userSystem.getEmail());
        loginRequest.setPassword(basePassString);
        return userAuthService.login(loginRequest);

    }
}
