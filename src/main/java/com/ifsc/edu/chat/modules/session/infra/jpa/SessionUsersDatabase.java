package com.ifsc.edu.chat.modules.session.infra.jpa;

import com.ifsc.edu.chat.core.user.infra.jpa.UserDatabase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "session_users")
@Getter
@Setter
public class SessionUsersDatabase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDatabase user;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private SessionDatabase session;
}
