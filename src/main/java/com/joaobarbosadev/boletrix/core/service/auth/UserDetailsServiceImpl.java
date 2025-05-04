package com.joaobarbosadev.boletrix.core.service.auth;

import com.joaobarbosadev.boletrix.core.models.auth.UserDetailsImpl;
import com.joaobarbosadev.boletrix.core.repository.UserSystemRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserSystemRepository userSystemRepository;

    public UserDetailsServiceImpl(UserSystemRepository userSystemRepository) {
        this.userSystemRepository = userSystemRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userSystemRepository.findByEmail(username).map(UserDetailsImpl::new).orElseThrow(()-> new UsernameNotFoundException("Não foi localizado usuário com o email informado: "+username));
    }
}
