package com.ifsc.edu.chat.modules.session.infra.repository;

import com.ifsc.edu.chat.modules.session.infra.jpa.SessionDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface SessionRepository extends JpaRepository<SessionDatabase, Long> {
    @Query("""
            SELECT s FROM SessionDatabase s
            INNER JOIN s.usersDatabases su1
            INNER JOIN s.usersDatabases su2
            WHERE su1.user.id = :myUserID AND su2.user.id = :userID
            """
    )
    SessionDatabase findSession(@Param("myUserID") Long myUserID, @Param("userID") Long userID);
}
