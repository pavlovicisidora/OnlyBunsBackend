package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query(value = """
    SELECT c.* 
    FROM chat c 
    JOIN group_members gm ON c.id = gm.chat_id 
    JOIN users u ON gm.user_id = u.id 
    WHERE u.username = :username
    """, nativeQuery = true)
    List<Chat> findAllChatsByUser(@Param("username") String username);
    @Query("SELECT c FROM Chat c JOIN c.members p1 JOIN c.members p2 " +
            "WHERE c.type = com.ISA.OnlyBunsBackend.model.ChatType.PRIVATE " +
            "AND p1.id = :id1 AND p2.id = :id2")
    Optional<Chat> findPrivateChatBetweenUsers(@Param("id1") int id1, @Param("id2") int id2);

}
