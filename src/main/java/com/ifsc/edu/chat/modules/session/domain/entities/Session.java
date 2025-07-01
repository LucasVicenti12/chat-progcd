package com.ifsc.edu.chat.modules.session.domain.entities;

import com.ifsc.edu.chat.core.user.domain.entities.User;
import com.ifsc.edu.chat.modules.messages.domain.entities.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Session {
    private Long id;
    private List<User> users;
    private List<Message> messages;
}
