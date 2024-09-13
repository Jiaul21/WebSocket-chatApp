package com.jiaul.websocket_chatapp_server.repository;

import com.jiaul.websocket_chatapp_server.entity.ChatGroup;
import com.jiaul.websocket_chatapp_server.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Set<ChatMessage> findByChatGroup(ChatGroup chatGroup);


    @Query("SELECT cm FROM ChatMessage cm WHERE cm.chatGroup = :chatGroup AND cm.id > :lastMessageId ORDER BY cm.id ASC")
    List<ChatMessage> findChatMessagesByChatGroupAndId(@Param("chatGroup") ChatGroup chatGroup,
                                                       @Param("lastMessageId") Long lastMessageId,
                                                       Pageable pageable);


}
