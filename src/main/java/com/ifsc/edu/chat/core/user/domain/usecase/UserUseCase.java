package com.ifsc.edu.chat.core.user.domain.usecase;

import com.ifsc.edu.chat.core.user.domain.entities.Login;
import com.ifsc.edu.chat.core.user.domain.entities.User;
import com.ifsc.edu.chat.core.user.domain.usecase.response.LoginResponse;
import com.ifsc.edu.chat.core.user.domain.usecase.response.UserListResponse;
import com.ifsc.edu.chat.core.user.domain.usecase.response.UserResponse;

public interface UserUseCase {
    UserResponse saveUser(User user);
    LoginResponse login(Login login);
    UserListResponse findAll();
}
