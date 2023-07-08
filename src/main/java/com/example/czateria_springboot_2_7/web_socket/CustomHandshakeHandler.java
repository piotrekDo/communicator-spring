package com.example.czateria_springboot_2_7.web_socket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes ) {
        String userName = request.getURI().toString().split("user=")[1];
        String uuid = UUID.randomUUID().toString();
        StompPrincipal stompPrincipal = new StompPrincipal(uuid);
        stompPrincipal.setUserName(userName);
        return stompPrincipal;
    }




}