package com.jiaul.websocket_chatapp_server.service;

import com.jiaul.websocket_chatapp_server.entity.ChatGroup;
import com.jiaul.websocket_chatapp_server.entity.ChatMessage;
import com.jiaul.websocket_chatapp_server.mapper.MapperService;
import com.jiaul.websocket_chatapp_server.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private MapperService mapperService;

    public Set<ChatMessage> getAllChatMessageByChatGroupId(Long chatGroupId, Long lastMessageId, Long numberOfMessage){
        ChatGroup chatGroup=ChatGroup.builder().id(chatGroupId).build();

        Pageable pageable = PageRequest.of(0, numberOfMessage.intValue());
        List<ChatMessage> chatMessages= chatMessageRepository.findChatMessagesByChatGroupAndId(chatGroup, lastMessageId, pageable);
        if(chatMessages!=null){
            chatMessages.forEach( chatMessage -> {
                chatMessage.setChatGroup(null);
                chatMessage.setParentChatMessage(mapperService.responseChatMessage(chatMessage.getParentChatMessage()));
                chatMessage.setSender(mapperService.responseGroupMember(chatMessage.getSender()));
            });
        }
        return Set.copyOf(chatMessages);
    }
}
