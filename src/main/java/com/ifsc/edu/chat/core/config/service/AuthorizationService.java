package com.ifsc.edu.chat.core.config.service;

import com.ifsc.edu.chat.core.config.entities.SystemUser;
import com.ifsc.edu.chat.core.user.domain.entities.User;
import com.ifsc.edu.chat.core.user.infra.jpa.UserDatabase;
import com.ifsc.edu.chat.core.user.infra.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthorizationService(UserRepository authRepository) {
        this.userRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDatabase user = userRepository.findByUsername(username);
        if (user != null) {
            final User userDTO = new User();

            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());

            return new SystemUser(userDTO);
        }
        return null;
    }
}