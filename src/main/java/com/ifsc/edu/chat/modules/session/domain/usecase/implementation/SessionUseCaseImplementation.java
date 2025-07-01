package com.ifsc.edu.chat.modules.session.domain.usecase.implementation;

import com.ifsc.edu.chat.core.config.entities.SystemUser;
import com.ifsc.edu.chat.core.user.domain.entities.User;
import com.ifsc.edu.chat.core.user.infra.jpa.UserDatabase;
import com.ifsc.edu.chat.core.user.infra.repository.UserRepository;
import com.ifsc.edu.chat.modules.session.domain.entities.Session;
import com.ifsc.edu.chat.modules.session.domain.usecase.SessionUseCase;
import com.ifsc.edu.chat.modules.session.domain.usecase.reponse.SessionResponse;
import com.ifsc.edu.chat.modules.session.infra.jpa.SessionDatabase;
import com.ifsc.edu.chat.modules.session.infra.jpa.SessionUsersDatabase;
import com.ifsc.edu.chat.modules.session.infra.repository.SessionRepository;
import com.ifsc.edu.chat.modules.session.infra.repository.SessionUsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionUseCaseImplementation implements SessionUseCase {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SessionUsersRepository sessionUsersRepository;

    public SessionUseCaseImplementation(
            SessionRepository sessionRepository,
            UserRepository userRepository,
            SessionUsersRepository sessionUsersRepository
    ) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.sessionUsersRepository = sessionUsersRepository;
    }

    @Transactional
    @Override
    public SessionResponse connectSession(Long userID) {
        final var myUser = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var session = sessionRepository.findSession(myUser.getUserData().getId(), userID);

        if (session != null) {
            final Session sessionDTO = new Session();
            final List<User> usersDTO = session.getUsersDatabases().stream().map(x -> {
                final var y = new User();

                y.setId(x.getUser().getId());
                y.setUsername(x.getUser().getUsername());

                return y;
            }).toList();

            sessionDTO.setId(session.getId());
            sessionDTO.setUsers(usersDTO);

            return new SessionResponse(sessionDTO, null);
        }

        SessionDatabase newSession = new SessionDatabase();

        newSession.setCreatedAt(LocalDateTime.now());

        newSession = sessionRepository.save(newSession);

        SessionUsersDatabase mySessionUser = new SessionUsersDatabase();

        Optional<UserDatabase> userDatabase = userRepository.findById(myUser.getUserData().getId());
        if (userDatabase.isPresent()) {
            mySessionUser.setSession(newSession);
            mySessionUser.setUser(userDatabase.get());
        } else {
            return new SessionResponse(null, "My user not found");
        }

        SessionUsersDatabase newSessionUser = new SessionUsersDatabase();

        Optional<UserDatabase> newUserDatabase = userRepository.findById(userID);
        if (newUserDatabase.isPresent()) {
            newSessionUser.setSession(newSession);
            newSessionUser.setUser(newUserDatabase.get());
        } else {
            return new SessionResponse(null, "User not found");
        }

        sessionUsersRepository.save(mySessionUser);
        sessionUsersRepository.save(newSessionUser);

        return this.connectSession(userID);
    }
}
