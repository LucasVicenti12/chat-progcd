package com.ifsc.edu.chat.modules.messages.domain.usecase;

import com.ifsc.edu.chat.modules.messages.domain.entities.Message;
import com.ifsc.edu.chat.modules.messages.domain.usecase.response.MessageResponse;

public interface MessageUseCase {
    MessageResponse saveMessage(Message message);
}
