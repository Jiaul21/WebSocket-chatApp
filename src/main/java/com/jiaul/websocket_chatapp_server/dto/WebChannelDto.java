package com.jiaul.websocket_chatapp_server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class WebChannelDto {

    private Long id;
    private String channelName;
    private String channelAddress;
    private String channelType; // message, notification or information channel

    private ChatGroupDto chatGroup;
}
