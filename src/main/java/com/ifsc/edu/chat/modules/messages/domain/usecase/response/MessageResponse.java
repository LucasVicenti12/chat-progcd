package com.ifsc.edu.chat.modules.messages.domain.usecase.response;

import com.ifsc.edu.chat.modules.messages.domain.entities.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageResponse {
    private Message message;
    private String error;
}
