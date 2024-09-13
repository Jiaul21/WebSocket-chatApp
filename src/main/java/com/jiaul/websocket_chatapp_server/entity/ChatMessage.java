package com.jiaul.websocket_chatapp_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jiaul.websocket_chatapp_server.enums.MessageEnumType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"sender","parentChatMessage","chatGroup"})
@EqualsAndHashCode(exclude = {"sender","parentChatMessage","chatGroup"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private MessageEnumType messageType;
    private String messageSource;
    private Date dateTime;
    private int vote;
    private boolean isVisible;

    @OneToOne
    private GroupMember sender;

    @OneToOne
    private ChatMessage parentChatMessage;

    @ManyToOne
    private ChatGroup chatGroup;
}
