package com.ifsc.edu.chat.modules.messages.infra.repository;

import com.ifsc.edu.chat.modules.messages.infra.database.MessageDatabase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageDatabase, Long> {}
