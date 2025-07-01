package com.ifsc.edu.chat.core.user.domain.usecase.response;

import com.ifsc.edu.chat.core.user.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private User user;
    private String error;
}
