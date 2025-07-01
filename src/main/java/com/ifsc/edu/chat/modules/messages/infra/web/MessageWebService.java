package com.ifsc.edu.chat.modules.messages.infra.web;

import com.ifsc.edu.chat.modules.messages.domain.entities.Message;
import com.ifsc.edu.chat.modules.messages.domain.usecase.MessageUseCase;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebService {
    private final MessageUseCase messageUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageWebService(
            MessageUseCase messageUseCase,
            SimpMessagingTemplate messagingTemplate
    ) {
        this.messageUseCase = messageUseCase;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    public void sendMessage(Message message) {
        final var response = messageUseCase.saveMessage(message);

        messagingTemplate.convertAndSend(
                "/topic/" + response.getMessage().getSessionID(),
                response
        );
    }
}
