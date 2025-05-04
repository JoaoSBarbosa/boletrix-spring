package com.joaobarbosadev.boletrix.core.models.auth;

import com.joaobarbosadev.boletrix.core.models.domain.UserSystem;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserDetailsImpl implements UserDetails {

    private final UserSystem userSystem;

    public UserDetailsImpl(UserSystem userSystem) {
        this.userSystem = userSystem;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userSystem.getRoles();
    }

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
