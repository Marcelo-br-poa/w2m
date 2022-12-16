package com.w2m.application.auth;

import com.w2m.adapter.out.persiste.user.UserEntity;
import com.w2m.adapter.out.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServiceUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findOneByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No existe un usuario con este nombre: %s", username)));

        return new UserDetailsImpl(userEntity);
    }
}
