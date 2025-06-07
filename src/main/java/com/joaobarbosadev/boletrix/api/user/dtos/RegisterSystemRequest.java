package com.joaobarbosadev.boletrix.api.user.dtos;

import com.joaobarbosadev.boletrix.core.models.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class RegisterSystemRequest {


    @NotNull(message = "O campo Nome é obrigatório para realizar o cadastro.")
    @Size(min = 3,max = 255)
    @NotBlank(message = "O campo Nome não pode estar vazio ou conter apenas espaços em branco.")
    private String name;


    @Email(message = "O campo E-mail deve conter um endereço de e-mail válido.")
    @NotNull(message = "O campo E-mail é obrigatório para realizar o login.")
    @Size(min = 3,max = 255)
    @NotBlank(message = "O campo E-mail não pode estar vazio ou conter apenas espaços em branco.")
    private String email;

    @NotNull(message = "O campo Senha é obrigatório.")
    @NotBlank(message = "O campo Senha não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 6, max = 255, message = "O campo Senha deve ter entre 6 e 255 caracteres.")
    private String password;


    @NotNull(message = "O campo Confirmação de Senha é obrigatório.")
    @NotBlank(message = "O campo Confirmação de Senha não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 6, max = 255, message = "O campo Confirmação de Senha deve ter entre 6 e 255 caracteres.")
    private String confirmationPassword;
    private Role roles;
    public RegisterSystemRequest(){}

    public RegisterSystemRequest(String email,Role roles, String name, String password, String confirmationPassword) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
        this.confirmationPassword = confirmationPassword;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterSystemRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmationPassword='" + confirmationPassword + '\'' +
                ", roles=" + roles +
                '}';
    }
}
