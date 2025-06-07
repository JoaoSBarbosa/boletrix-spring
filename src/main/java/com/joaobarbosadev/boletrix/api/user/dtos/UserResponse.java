package com.joaobarbosadev.boletrix.api.user.dtos;

import com.joaobarbosadev.boletrix.core.models.domain.Role;
import com.joaobarbosadev.boletrix.core.models.domain.UserSystem;

import java.util.HashSet;
import java.util.Set;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Set<Role> roles = new HashSet<>();

    public UserResponse(){}

    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserResponse(UserSystem entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();

    }
    public UserResponse(UserSystem entity, Set<Role> roles) {
        this(entity);
        this.roles.addAll(roles);
    }

        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
