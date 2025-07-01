package com.ifsc.edu.chat.modules.messages.infra.database;

import com.ifsc.edu.chat.core.user.infra.jpa.UserDatabase;
import com.ifsc.edu.chat.modules.session.infra.jpa.SessionDatabase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class MessageDatabase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private SessionDatabase session;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDatabase user;
}
