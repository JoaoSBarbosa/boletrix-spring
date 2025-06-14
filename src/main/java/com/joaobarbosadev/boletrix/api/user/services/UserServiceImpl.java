package com.joaobarbosadev.boletrix.api.user.services;

import com.joaobarbosadev.boletrix.api.auth.dto.LoginRequest;
import com.joaobarbosadev.boletrix.api.auth.dto.LoginResponse;
import com.joaobarbosadev.boletrix.api.auth.services.AuthService;
import com.joaobarbosadev.boletrix.api.user.dtos.*;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEntityNotFoundException;
import com.joaobarbosadev.boletrix.core.models.domain.Role;
import com.joaobarbosadev.boletrix.core.models.domain.UserSystem;
import com.joaobarbosadev.boletrix.core.repository.RoleRepository;
import com.joaobarbosadev.boletrix.core.repository.UserSystemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserSystemRepository userSystemRepository;
    private final AuthService userAuthService;
    private final RoleRepository roleRepository;


    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserSystemRepository userSystemRepository, AuthService userAuthService, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userSystemRepository = userSystemRepository;
        this.userAuthService = userAuthService;
        this.roleRepository = roleRepository;
    }

    @Override
    public LoginResponse registerAndLogin(RegisterRequest registerRequest) {
        checkValues(registerRequest);
        String encodedPassword = encoderPassword(registerRequest.getPassword());
        UserSystem userSystem = new UserSystem();
        buildUserSystem(registerRequest, userSystem, encodedPassword);
        userSystem = userSystemRepository.save(userSystem);

        return authenticateUser(userSystem, registerRequest.getPassword());
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserSystem> userSystems = userSystemRepository.findAll();
        return userSystems.stream().map(user -> new UserResponse(user, user.getRoles())).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        if (id == null) {
            throw new CustomEmptyFieldException("ID está nulo");
        }
        return userSystemRepository.findById(id).map(UserResponse::new).orElseThrow(() -> new CustomEntityNotFoundException("Não foi localizado usuario com o id: " + id));
    }

    @Override
    public UserResponse editUser(UserRequest request, Long id) {
        if (id == null) {
            throw new CustomEmptyFieldException("ID est<UNK> nulo");
        }
        checkingFields(request);

        UserSystem user = getUser(id);

        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.getRoles().clear();

        // Busca e adiciona os novos acessos
        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new CustomEntityNotFoundException("Acesso não encontrado: " + roleName));
            user.getRoles().add(role);
        }

        userSystemRepository.save(user);
        return new UserResponse(user, user.getRoles());
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            UserSystem userSystem = userSystemRepository.getReferenceById(id);
            userSystemRepository.delete(userSystem);
        }catch (EntityNotFoundException e){
            throw new CustomEntityNotFoundException("Não foi localizado usuario com o id: " + id);
        }
    }

    @Override
    public LoginResponse updateProfile(UserUpdate userUpdate, Long id) {
        System.out.println("ID da rota: " + id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Usuário autenticado: " + auth.getName());
        System.out.println("Usuário: " + auth.getPrincipal());

        checkId(id);



        UserSystem userSystem = getUser(id);
        // Atualização de senha
        if (userUpdate.isAlterPassword()){
            System.out.println("SIM, É PARA ATUALIZAR A SENHA: " + userUpdate.isAlterPassword());
            validateCurrentPassword(userUpdate.getPassword(), userSystem.getPassword());
            validateNewPasswordFields(userUpdate);
            String newPassword = encoderPassword(userUpdate.getNewPassword());
            System.out.println("Nova senha: " + userUpdate.getNewPassword());
            System.out.println("Nova senha criptografada: " + newPassword);
            userSystem.setPassword(newPassword);
        }

        String newPasswordString = userUpdate.getNewPassword();

        // Atualização de nome
        if (userUpdate.getName() != null && !userUpdate.getName().isBlank()) {
            userSystem.setName(userUpdate.getName());
        }
        // Atualização de e-mail
        if (userUpdate.getEmail() != null && !userUpdate.getEmail().isBlank()) {
            userSystem.setEmail(userUpdate.getEmail());
        }


        userSystem = userSystemRepository.save(userSystem);
        System.out.println("USER: " + userSystem);
//
        LoginRequest loginRequest = new LoginRequest(userSystem.getEmail(), newPasswordString);
        return userAuthService.login(loginRequest);

//        return null;

    }
    @Override
    public UserResponse register(RegisterSystemRequest registerRequest) {
        checkValues2(registerRequest);
        String encodedPassword = encoderPassword(registerRequest.getPassword());
        UserSystem userSystem = new UserSystem();
        buildUserSystem2(registerRequest, userSystem, encodedPassword);
        userSystem = userSystemRepository.save(userSystem);
        return new UserResponse(userSystem, userSystem.getRoles());
    }





    private void validateCurrentPassword(String providedPassword, String currentEncodedPassword) {
        if (providedPassword == null || providedPassword.isBlank()) {
            throw new CustomEmptyFieldException("O campo 'Senha Atual' é obrigatório.");
        }

        // VERIFICA SE A SENHA INSERIDO E A SENHA NO USUARIO SÃO AS MESMAS
        if (!passwordEncoder.matches(providedPassword, currentEncodedPassword)) {
            throw new IllegalArgumentException("A senha atual está incorreta.");
        }
    }
    private void validateNewPasswordFields(UserUpdate userUpdate) {
        if (userUpdate.getNewPassword() == null || userUpdate.getNewPassword().isBlank()) {
            throw new CustomEmptyFieldException("O campo 'Nova Senha' é obrigatório.");
        }
        if (userUpdate.getConfirmNewPassword() == null || userUpdate.getConfirmNewPassword().isBlank()) {
            throw new CustomEmptyFieldException("O campo 'Confirmação de Nova Senha' é obrigatório.");
        }
        if (!userUpdate.getNewPassword().equals(userUpdate.getConfirmNewPassword())) {
            throw new IllegalArgumentException("A nova senha e a confirmação de nova senha não coincidem.");
        }
    }
    private String encoderPassword(String password) {
        if (password == null) {
            throw new CustomEmptyFieldException("Erro ao tenatr codigicar senha: -> Valor esta nulo ou vazio");
        }
        return passwordEncoder.encode(password);
    }
    private void checkingFields(UserRequest userRequest) {

        if ( userRequest.getEmail() == null || userRequest.getEmail().isEmpty()) {
            throw new CustomEmptyFieldException("Email est<UNK> nulo");
        }
        if ( userRequest.getName() == null || userRequest.getName().isEmpty()) {
            throw new CustomEmptyFieldException("Nome est<UNK> nulo");
        }
        if ( userRequest.getRoles() == null || userRequest.getRoles().isEmpty()) {
            throw new CustomEmptyFieldException("Roles est<UNK> nulo");
        }
    }

    private void buildUserSystem(RegisterRequest request, UserSystem userSystem, String encodedPassword) {
        userSystem.setPassword(encodedPassword);
        userSystem.setEmail(request.getEmail());
        userSystem.setName(request.getName());
        // ✅ Adicionando ROLE_USER por padrão
        Role defaultRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Acesso não encontrado: ROLE_USER"));

        userSystem.getRoles().add(defaultRole);
    }


    private UserSystem getUser(Long id) {
        return userSystemRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException("Não foi possivel loclaizar usuario com o id: " + id));

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

    private void checkValues2(RegisterSystemRequest registerRequest) {
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new CustomEmptyFieldException("O campo E-mail deve ser preenchido.");
        }
        validatePasswords2(registerRequest);
    }

    private void  checkId(Long id) {
        if (id == null) {
            throw new CustomEmptyFieldException("ID est<UNK> nulo");
        }
    }
    private void validatePasswords2(RegisterSystemRequest registerRequest) {
        String password = registerRequest.getPassword();
        String confirm = registerRequest.getConfirmationPassword();
        if (password == null || confirm == null || password.isBlank() || confirm.isBlank()) {
            throw new CustomEmptyFieldException("A senha e a confirmação de senha devem ser preenchidas.");
        }

        if ( registerRequest.getRoles() == null ) {
            throw new IllegalArgumentException("O campo Roles deve ser preenchido.");
        }
        if (!password.equals(confirm)) {
            throw new IllegalArgumentException("A senha e a confirmação de senha não coincidem.");
        }
    }
    private void buildUserSystem2(RegisterSystemRequest request, UserSystem userSystem, String encodedPassword) {
        userSystem.setPassword(encodedPassword);
        userSystem.setEmail(request.getEmail());
        userSystem.setName(request.getName());
        // ✅ Adicionando ROLE_USER por padrão

        Role defaultRole = roleRepository.findByName(request.getRoles().getName()).orElseThrow(() -> new RuntimeException("Acesso não encontrado: ROLE_USER"));

        userSystem.getRoles().add(defaultRole);
    }
    private LoginResponse authenticateUser(UserSystem userSystem, String basePassString) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(userSystem.getEmail());
        loginRequest.setPassword(basePassString);
        return userAuthService.login(loginRequest);

    }
}
