package com.ifsc.edu.chat.modules.session.domain.usecase;

import com.ifsc.edu.chat.modules.session.domain.usecase.reponse.SessionResponse;

public interface SessionUseCase {
    SessionResponse connectSession(Long userID);
}
