package com.ifsc.edu.chat.modules.messages.domain.usecase.implementation;

import com.ifsc.edu.chat.core.config.entities.SystemUser;
import com.ifsc.edu.chat.core.user.domain.entities.User;
import com.ifsc.edu.chat.core.user.infra.repository.UserRepository;
import com.ifsc.edu.chat.modules.messages.domain.entities.Message;
import com.ifsc.edu.chat.modules.messages.domain.usecase.MessageUseCase;
import com.ifsc.edu.chat.modules.messages.domain.usecase.response.MessageResponse;
import com.ifsc.edu.chat.modules.messages.infra.database.MessageDatabase;
import com.ifsc.edu.chat.modules.messages.infra.repository.MessageRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageUseCaseImplementation implements MessageUseCase {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public MessageUseCaseImplementation(
            UserRepository userRepository,
            MessageRepository messageRepository
    ) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageResponse saveMessage(Message message) {
        final var myUser = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final var userData = userRepository.findById(myUser.getUserData().getId());

        if(userData.isPresent()){
            MessageDatabase newMessage = new MessageDatabase();

            newMessage.setUser(userData.get());
            newMessage.setCreatedAt(LocalDateTime.now());
            newMessage.setText(message.getText());

            newMessage = messageRepository.save(newMessage);

            final Message messageDTO = new Message();
            final User userDTO = new User();

            userDTO.setId(newMessage.getUser().getId());
            userDTO.setUsername(newMessage.getUser().getUsername());

            messageDTO.setDateTime(newMessage.getCreatedAt());
            messageDTO.setAuthor(userDTO);
            messageDTO.setText(newMessage.getText());
            messageDTO.setSessionID(newMessage.getSession().getId());

            return new MessageResponse(messageDTO, null);
        }else{
            return new MessageResponse(null, "User not found");
        }
    }
}
