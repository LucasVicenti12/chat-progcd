package com.ifsc.edu.chat.core.user.domain.usecase.response;

import com.ifsc.edu.chat.core.user.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserListResponse {
    private List<User> users;
    private String error;
}
