package com.ifsc.edu.chat.core.user.domain.usecase.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String error;
}
