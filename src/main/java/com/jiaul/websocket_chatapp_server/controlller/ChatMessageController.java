package com.jiaul.websocket_chatapp_server.controlller;

import com.jiaul.websocket_chatapp_server.entity.ChatMessage;
import com.jiaul.websocket_chatapp_server.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController("/chat")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/get-all/by")
    public ResponseEntity<Set<ChatMessage>> getAllChatMessageByChatGroupId(
            @RequestParam(value = "groupId") Long chatGroupId,
            @RequestParam(value = "lastMessageId") Long lastMessageId,
            @RequestParam(value = "numberOfMessage") Long numberOfMessage){
        return ResponseEntity.ok(chatMessageService.getAllChatMessageByChatGroupId(chatGroupId,lastMessageId,numberOfMessage));
    }
}
