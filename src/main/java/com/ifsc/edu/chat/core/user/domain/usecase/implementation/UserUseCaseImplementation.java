package com.ifsc.edu.chat.core.user.domain.usecase.implementation;

import com.ifsc.edu.chat.core.config.entities.SystemUser;
import com.ifsc.edu.chat.core.config.service.TokenService;
import com.ifsc.edu.chat.core.user.domain.entities.Login;
import com.ifsc.edu.chat.core.user.domain.entities.User;
import com.ifsc.edu.chat.core.user.domain.usecase.UserUseCase;
import com.ifsc.edu.chat.core.user.domain.usecase.response.LoginResponse;
import com.ifsc.edu.chat.core.user.domain.usecase.response.UserListResponse;
import com.ifsc.edu.chat.core.user.domain.usecase.response.UserResponse;
import com.ifsc.edu.chat.core.user.infra.jpa.UserDatabase;
import com.ifsc.edu.chat.core.user.infra.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserUseCaseImplementation implements UserUseCase {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserUseCaseImplementation(
            UserRepository userRepository,
            TokenService tokenService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse saveUser(User user) {
        final UserDatabase temp = userRepository.findByUsername(user.getUsername());

        if (temp == null) {
            final var newUser = new UserDatabase();

            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));

            final UserDatabase saved = userRepository.save(newUser);

            if (saved != null) {
                final User userDTO = new User();

                userDTO.setUsername(saved.getUsername());

                return new UserResponse(userDTO, null);
            }

            return new UserResponse(null, "User not created");
        }

        return new UserResponse(null, "User already exists");
    }

    @Override
    public LoginResponse login(Login login) {
        if (login.getUsername() == null || login.getUsername().isEmpty()) {
            return new LoginResponse(null, "Login is empty");
        }

        if (login.getPassword() == null || login.getPassword().isEmpty()) {
            return new LoginResponse(null, "Password is empty");
        }

        final var username = new UsernamePasswordAuthenticationToken(
                login.getUsername(),
                login.getPassword()
        );
        final var auth = authenticationManager.authenticate(username);
        final var user = (SystemUser) auth.getPrincipal();

        final var token = tokenService.generate(user.getUserData());

        return new LoginResponse(token, null);
    }

    @Override
    public UserListResponse findAll() {
        final var users = userRepository.findAll();

        final var usersDTO = users.stream().map(x -> {
            final var y = new User();
            y.setUsername(x.getUsername());
            y.setId(x.getId());

            return y;
        }).toList();

        return new UserListResponse(usersDTO, null);
    }
}
