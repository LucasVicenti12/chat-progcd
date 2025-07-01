package com.ifsc.edu.chat.modules.session.domain.usecase.reponse;

import com.ifsc.edu.chat.modules.session.domain.entities.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionResponse {
    private Session session;
    private String error;
}
