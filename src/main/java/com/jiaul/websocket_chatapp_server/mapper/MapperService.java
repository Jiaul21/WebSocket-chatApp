package com.jiaul.websocket_chatapp_server.mapper;

import com.jiaul.websocket_chatapp_server.dto.AuthResponseDto;
import com.jiaul.websocket_chatapp_server.dto.ChatGroupDto;
import com.jiaul.websocket_chatapp_server.dto.RegistrationRequestDto;
import com.jiaul.websocket_chatapp_server.dto.WebChannelDto;
import com.jiaul.websocket_chatapp_server.entity.*;
import com.jiaul.websocket_chatapp_server.enums.AuthEnum;
import com.jiaul.websocket_chatapp_server.enums.ChannelEnumType;
import com.jiaul.websocket_chatapp_server.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class MapperService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public AuthResponseDto createAuthResponse(Token token){
        UserCredential userCredential= token.getUserCredential();
        Profile profile=userCredential.getProfile();
        return AuthResponseDto.builder()
                .userName(userCredential.getUsername())
                .profileId(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .photo(profile.getPhoto())
                .gender(profile.getGender())
                .language(profile.getLanguage())
                .country(profile.getCountry())
                .isActive(profile.isActive())

                .responseMessage(AuthEnum.LOGIN_SUCCESS)
                .jwtTokenValue(token.getJwtToken())
                .jwtTokenType(token.getJwtTokenType())
                .webSocketToken(token.getWebSocketToken())
                .tokenExpirationDateTime(jwtService.getExpirationDateTime())

                .webChannels(responseWebChannels(profile.getWebChannels()))
                .build();
    }

    public Set<WebChannel> responseWebChannels(Set<WebChannel> webChannels){
        webChannels.forEach(webChannel -> {
            webChannel.setProfiles(null);
            webChannel.setChatGroup(responseChatGroup(webChannel.getChatGroup()));
        });
        return webChannels;
    }

    public WebChannel createWebChannel_By_UserCredential(UserCredential userCredential){
        return WebChannel.builder()
                .channelName(userCredential.getUsername())
                .messageChannel("user/message/"+userCredential.getUsername())
                .notifyChannel("/user/notify/"+userCredential.getUsername())
                .activityChannel("/user/activity/"+userCredential.getUsername())
                .infoChannel("/user/account/"+userCredential.getUsername())
                .channelType(ChannelEnumType.USER)
//                .profiles(Set.of(userCredential.getProfile()))
                .build();
    }

    public ChatGroup responseChatGroup(ChatGroup chatGroup){
        if(chatGroup!=null){
            chatGroup.setChatMessages(null);
            chatGroup.setGroupMembers(null);
            chatGroup.setChatMessages(null);
        }
        return chatGroup;
    }

    public ChatMessage responseChatMessage(ChatMessage chatMessage){
        if(chatMessage!=null){
            chatMessage.setParentChatMessage(null);
            chatMessage.setChatGroup(null);
            chatMessage.setSender(null);
        }
        return chatMessage;
    }

    public GroupMember responseGroupMember(GroupMember groupMember){
        if(groupMember!=null){
            groupMember.setProfile(null);
            groupMember.setAddedBy(null);
        }
        return groupMember;
    }

    public Token createJwtToken_By_UserCredential(UserCredential userCredential){
        return Token.builder()
                .jwtToken(jwtService.generateToken(userCredential))
                .jwtTokenType("Bearer")
                .isExpired(false)
                .isLogOut(false)
                .webSocketToken(AuthEnum.WEB_SOCKET_IS_NOT_CONNECTED.toString())
                .userCredential(userCredential)
                .build();
    }
    public Profile createProfile_By_RegistrationRequestDto(RegistrationRequestDto registrationRequestDto){
        return Profile.builder()
                .firstName(registrationRequestDto.getFirstName())
                .isActive(true)
                .build();
    }
    public UserCredential createUserCredential_By_RegistrationRequestDto(RegistrationRequestDto registrationRequestDto){
        return UserCredential.builder()
                .userName(registrationRequestDto.getUserName())
                .password(passwordEncoder.encode(registrationRequestDto.getPassword()))
                .role(registrationRequestDto.getRole())
                .enabled(true)
                .build();
    }
}
