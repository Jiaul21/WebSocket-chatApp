package com.jiaul.websocket_chatapp_server.dto;

import com.jiaul.websocket_chatapp_server.entity.ChatMessage;
import com.jiaul.websocket_chatapp_server.entity.GroupMember;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@ToString
public class ChatGroupDto {

    private Long id;
    private String groupName;
    private String photo;
    private Date createTime;
    private boolean isActive;
    private String channel;
    private boolean isPersonal;  // one to one chat

    private WebChannelDto webChannelDto;
    private Set<ChatMessageDto> chatMessages;
    private Set<GroupMemberDto> groupMembers;
}
