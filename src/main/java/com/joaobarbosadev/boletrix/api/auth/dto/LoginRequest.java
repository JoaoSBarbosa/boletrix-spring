package com.joaobarbosadev.boletrix.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class LoginRequest {
    @Email(message = "O campo E-mail deve conter um endereço de e-mail válido.")
    @NotNull(message = "O campo E-mail é obrigatório para realizar o login.")
    @Size(min = 3,max = 255)
    @NotBlank(message = "O campo E-mail não pode estar vazio ou conter apenas espaços em branco.")
    private String email;

    @NotNull(message = "O campo Senha é obrigatório.")
    @NotBlank(message = "O campo Senha não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 6, max = 255, message = "O campo Senha deve ter entre 6 e 255 caracteres.")
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
