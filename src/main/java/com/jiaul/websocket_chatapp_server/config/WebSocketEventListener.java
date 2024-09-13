package com.jiaul.websocket_chatapp_server.config;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

@Component
@AllArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        System.out.println("session disconnected: "+event);

//        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
//        String username= (String) headerAccessor.getSessionAttributes().get("username");
//        if (username != null) {
//            log.info("User Disconnected: ",username);
//            var chatMessage= ChatMessage.builder()
//                    .type(MessageType.LEAVE)
//                    .sender(username)
//                    .build();
//
//            messageTemplate.convertAndSend("/topic/public",chatMessage);
//        }
//        messageTemplate.convertAndSend("/topic/public",event);
    }

    @EventListener
    public void handleWebsocketConnectListener(SessionConnectEvent event) {    // work before connect
        System.out.println("session connect event: " + event);
    }

    @EventListener
    public void handleWebsocketConnectedListener(SessionConnectedEvent event) {  // work after connect successfully
        System.out.println("session connected event: " + event);
    }

    @EventListener
    public void Subscribed(SessionSubscribeEvent event) {
        System.out.println("Subscribed event: " + event);
    }

    @EventListener
    public void unSubscribed(SessionUnsubscribeEvent event) {
        System.out.println("Unsubscribed event: " + event);
    }

}
