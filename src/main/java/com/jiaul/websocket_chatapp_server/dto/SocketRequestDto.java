package com.jiaul.websocket_chatapp_server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class SocketRequestDto {

//    private String jwtToken;
    private String webSocketToken;
    private String response;
}
