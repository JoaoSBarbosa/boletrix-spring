package com.joaobarbosadev.boletrix.api.user.dtos;

import com.joaobarbosadev.boletrix.core.models.domain.Role;
import jakarta.persistence.Column;

import java.util.Set;

public class UserRequest {

    private String name;
    private String email;
    private Set<String> roles; // exemplo: ["ROLE_USER"]

    public UserRequest(){}

    public UserRequest(String name, Set<String> roles, String email) {
        this.name = name;
        this.roles = roles;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
