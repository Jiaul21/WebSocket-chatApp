package com.jiaul.websocket_chatapp_server.controlller;

import com.jiaul.websocket_chatapp_server.dto.SocketRequestDto;
import com.jiaul.websocket_chatapp_server.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/get/ws-token")
    public ResponseEntity<SocketRequestDto> getWebSocketToken(@RequestHeader(value = "Authorization") String jwtToken){
        return ResponseEntity.ok(tokenService.getWebSocketToken(jwtToken.substring(7)));
    }
}
