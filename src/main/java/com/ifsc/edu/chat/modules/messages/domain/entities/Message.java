package com.ifsc.edu.chat.modules.messages.domain.entities;

import com.ifsc.edu.chat.core.user.domain.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private Long id;
    private String text;
    private User author;
    private LocalDateTime dateTime;
    private Long sessionID;
}
