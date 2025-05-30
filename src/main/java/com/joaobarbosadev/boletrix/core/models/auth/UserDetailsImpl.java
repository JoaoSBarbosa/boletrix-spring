package com.joaobarbosadev.boletrix.core.models.auth;

import com.joaobarbosadev.boletrix.core.models.domain.UserSystem;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails {

    private final UserSystem userSystem;

    public UserDetailsImpl(UserSystem userSystem) {
        this.userSystem = userSystem;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userSystem.getRoles().stream()
                .map(role -> {
                    String roleName = role.getName();
                    // Evita duplicar ROLE_
                    if (!roleName.startsWith("ROLE_")) {
                        roleName = "ROLE_" + roleName;
                    }
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList());
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return userSystem.getRoles();
//    }

    @Override
    public String getPassword() {
        return this.userSystem.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userSystem.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserSystem getUserSystem() {
        return userSystem;
    }
}
