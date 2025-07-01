package com.ifsc.edu.chat.modules.session.infra.repository;

import com.ifsc.edu.chat.modules.session.infra.jpa.SessionUsersDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface SessionUsersRepository extends JpaRepository<SessionUsersDatabase, Long> {}
