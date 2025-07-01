package com.ifsc.edu.chat.core.config.entities;

import com.ifsc.edu.chat.core.user.domain.entities.User;

import java.util.List;

public class SystemUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public SystemUser(User user) {
        super(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                List.of()
        );
        this.user = user;
    }

    public User getUserData(){
        return this.user;
    }
}
