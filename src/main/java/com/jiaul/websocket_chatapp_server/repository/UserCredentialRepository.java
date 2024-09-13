package com.jiaul.websocket_chatapp_server.repository;

import com.jiaul.websocket_chatapp_server.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    Optional<UserDetails> findByUserName(String username);

    boolean existsByUserName(String userName);

}
