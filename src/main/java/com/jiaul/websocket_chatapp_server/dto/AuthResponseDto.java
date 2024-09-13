package com.jiaul.websocket_chatapp_server.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiaul.websocket_chatapp_server.entity.WebChannel;
import com.jiaul.websocket_chatapp_server.enums.AuthEnum;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.DataTruncation;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@ToString
public class AuthResponseDto {

    private String userName;
    private Long profileId;
    private String firstName;
    private String lastName;
    private String photo;
    private String gender;
    private String language;
    private String country;
    private boolean isActive;

    private AuthEnum responseMessage;
    private String jwtTokenValue;
    private String jwtTokenType;
    private String webSocketToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss a")
    private Date tokenExpirationDateTime;

    private Set<WebChannel> webChannels;
}
