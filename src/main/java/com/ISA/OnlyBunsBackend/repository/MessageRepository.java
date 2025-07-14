package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = """
    SELECT * 
    FROM messages m 
    WHERE m.chat_id = :chatId
    ORDER BY m.timestamp ASC
    """, nativeQuery = true)
    List<Message> findMessagesByChat(@Param("chatId") Integer chatId);
    @Query(value = """
    SELECT * FROM messages 
    WHERE chat_id = :chatId 
    ORDER BY timestamp DESC 
    LIMIT 10
    """, nativeQuery = true)
    List<Message> findLast10Messages(@Param("chatId") Integer chatId);
}
