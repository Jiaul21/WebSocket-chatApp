package com.jiaul.websocket_chatapp_server.service;

import com.jiaul.websocket_chatapp_server.dto.SocketRequestDto;
import com.jiaul.websocket_chatapp_server.entity.Token;
import com.jiaul.websocket_chatapp_server.enums.AuthEnum;
import com.jiaul.websocket_chatapp_server.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token saveJwtToken(Token token) {
        return tokenRepository.save(token);
    }


    public String deleteTokenByJwtToken(String jwtToken) {
        tokenRepository.deleteByJwtToken(jwtToken);
        return AuthEnum.LOGOUT_SUCCESS.toString();
    }

    public SocketRequestDto getWebSocketToken(String jwtToken) {
        SocketRequestDto socketRequestDto =new SocketRequestDto();
        if (tokenRepository.existsByJwtToken(jwtToken)) {
            Token token = tokenRepository.findByJwtToken(jwtToken);

            token.setWebSocketToken(UUID.randomUUID().toString().replace("-", ""));
            token = tokenRepository.save(token);

            socketRequestDto.setResponse(AuthEnum.WEB_SOCKET_TOKEN_UPDATED.toString());
            socketRequestDto.setWebSocketToken(token.getWebSocketToken());
            return socketRequestDto;
        }
        socketRequestDto.setResponse("FAILED");
        return socketRequestDto;
    }

    public boolean isAbleToWobSocketConnect(String webSocketToken) {
        if (tokenRepository.existsByWebSocketToken(webSocketToken)) {
            if (tokenRepository.updateWebSocketTokenByWebSocketToken(AuthEnum.WEB_SOCKET_CONNECTED.toString(), webSocketToken) > 0) {
                return true;
            }
        }
        return false;
    }
}