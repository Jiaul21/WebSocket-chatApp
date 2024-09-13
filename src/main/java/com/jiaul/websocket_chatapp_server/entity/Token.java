package com.jiaul.websocket_chatapp_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userCredential"})
@EqualsAndHashCode(exclude = "userCredential") // It won't check circular reference by equals() and hashCode() for "userCredential"
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jwtToken;
    private String jwtTokenType;
    private String webSocketToken;
    private boolean isExpired;
    private boolean isLogOut;

    @ManyToOne
    private UserCredential userCredential;
}
