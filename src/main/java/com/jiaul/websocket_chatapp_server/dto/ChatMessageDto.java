package com.jiaul.websocket_chatapp_server.dto;


import com.jiaul.websocket_chatapp_server.entity.ChatGroup;
import com.jiaul.websocket_chatapp_server.entity.ChatMessage;
import com.jiaul.websocket_chatapp_server.entity.GroupMember;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
@ToString
public class ChatMessageDto {

    private Long id;
    private String message;
    private String messageType;
    private String messageSource;
    private Date dateTime;
    private int vote;
    private boolean isVisible;

    private GroupMemberDto sender;
    private ChatMessageDto parentChatMessage;
    private ChatGroupDto chatGroup;
}
