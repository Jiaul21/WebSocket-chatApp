package com.jiaul.websocket_chatapp_server.dto;

import com.jiaul.websocket_chatapp_server.entity.Profile;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class GroupMemberDto {

    private Long id;
    private String nickName;
    private boolean isAdmin;
    private boolean isActive;
    private boolean isNotificationActive;
    private Long lastSeenMessageId;

    private Profile profile;
}
