package com.ifsc.edu.chat.modules.session.infra.web;

import com.ifsc.edu.chat.modules.session.domain.usecase.SessionUseCase;
import com.ifsc.edu.chat.modules.session.domain.usecase.reponse.SessionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/session")
@RestController
public class SessionWebService {
    private final SessionUseCase sessionUseCase;

    public SessionWebService(SessionUseCase sessionUseCase) {
        this.sessionUseCase = sessionUseCase;
    }

    @PostMapping("/connect/{userID}")
    public ResponseEntity<SessionResponse> connectSession(
            @PathVariable(name = "userID", required = true) Long userID
    ) {
        final var response = sessionUseCase.connectSession(userID);

        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
