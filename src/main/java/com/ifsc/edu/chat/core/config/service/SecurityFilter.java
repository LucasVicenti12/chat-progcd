package com.ifsc.edu.chat.core.config.service;

import com.ifsc.edu.chat.core.config.entities.SystemUser;
import com.ifsc.edu.chat.core.user.domain.entities.User;
import com.ifsc.edu.chat.core.user.infra.jpa.UserDatabase;
import com.ifsc.edu.chat.core.user.infra.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String token = tokenService.recoverToken(request);

        if (token != null && !token.isBlank()) {
            final String username = tokenService.validate(token);

            final UserDatabase user = userRepository.findByUsername(username);

            if (user != null) {
                final User userDTO = new User();

                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setPassword(user.getPassword());

                final SystemUser systemUser = new SystemUser(userDTO);

                var authentication = new UsernamePasswordAuthenticationToken(
                        systemUser,
                        null,
                        systemUser.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new AccessDeniedException("Invalid token");
            }
        }

        filterChain.doFilter(request, response);
    }
}