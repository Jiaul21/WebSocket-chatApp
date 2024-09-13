package com.jiaul.websocket_chatapp_server.repository;

import com.jiaul.websocket_chatapp_server.entity.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Transactional
    void deleteByJwtToken(String jwtToken);

    Token findByJwtToken(String jwtToken);

    boolean existsByWebSocketToken(String webSocketToken);

    @Modifying
    @Transactional
    @Query("UPDATE Token t SET t.webSocketToken = :newWebSocketToken WHERE t.webSocketToken = :oldWebSocketToken")
    int updateWebSocketTokenByWebSocketToken(@Param("newWebSocketToken") String newWebSocketToken,
                                             @Param("oldWebSocketToken") String oldWebSocketToken);

    boolean existsByJwtToken(String jwtToken);
}
