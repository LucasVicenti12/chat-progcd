package com.ifsc.edu.chat.core.user.infra.web;

import com.ifsc.edu.chat.core.config.service.TokenService;
import com.ifsc.edu.chat.core.user.domain.entities.Login;
import com.ifsc.edu.chat.core.user.domain.entities.User;
import com.ifsc.edu.chat.core.user.domain.usecase.UserUseCase;
import com.ifsc.edu.chat.core.user.domain.usecase.response.LoginResponse;
import com.ifsc.edu.chat.core.user.domain.usecase.response.UserListResponse;
import com.ifsc.edu.chat.core.user.domain.usecase.response.UserResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserWebService {
    private final UserUseCase userUseCase;
    private final TokenService tokenService;

    public UserWebService(
            UserUseCase userUseCase,
            TokenService tokenService
    ) {
        this.userUseCase = userUseCase;
        this.tokenService = tokenService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        final var response = userUseCase.saveUser(user);

        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(Login login) {
        final var response = userUseCase.login(login);

        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, tokenService.generateTokenCookie(response.getToken()).toString())
                .header("HX-Redirect", "/app/home")
                .body(response);
    }

    @GetMapping("/findAll")
    public ResponseEntity<UserListResponse> findAll() {
        final var response = userUseCase.findAll();

        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
