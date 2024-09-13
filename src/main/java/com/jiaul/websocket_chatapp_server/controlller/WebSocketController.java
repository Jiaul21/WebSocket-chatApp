package com.jiaul.websocket_chatapp_server.controlller;

import com.jiaul.websocket_chatapp_server.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/chat")
    public void sentMessage(@Payload ChatMessage chatMessage){

    }
}
