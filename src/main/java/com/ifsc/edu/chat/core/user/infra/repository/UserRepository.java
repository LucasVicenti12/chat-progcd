package com.ifsc.edu.chat.core.user.infra.repository;

import com.ifsc.edu.chat.core.user.infra.jpa.UserDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<UserDatabase, Long> {
    UserDatabase findByUsername(String username);
}
