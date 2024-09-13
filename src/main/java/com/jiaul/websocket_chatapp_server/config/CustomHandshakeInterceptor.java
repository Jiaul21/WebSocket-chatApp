package com.jiaul.websocket_chatapp_server.config;


import com.jiaul.websocket_chatapp_server.service.TokenService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.net.URI;
import java.util.Map;

@Component
public class CustomHandshakeInterceptor extends HttpSessionHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("++++++++++++++++++ beforeHandshake ++++++++++++++++++++++++");


        URI uri = request.getURI();
        String query = uri.getQuery();
        if (query != null && query.contains("token")) {
            String token = query.split("=")[1];
            System.out.println("Token: " + token);
            if (tokenService.isAbleToWobSocketConnect(token)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private HttpSession getSession(ServerHttpRequest request) {
        System.out.println("++++++++++++++++++ getSession ++++++++++++++++++++++++");
        if (request instanceof ServletServerHttpRequest serverRequest) {
            return serverRequest.getServletRequest().getSession(isCreateSession());
        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, @Nullable Exception ex) {
        System.out.println("++++++++++++++++++ afterHandshake ++++++++++++++++++++++++");
    }
}

