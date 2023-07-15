package com.example.czateria_springboot_2_7.web_socket;

import com.example.czateria_springboot_2_7.public_chat.SystemMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;

@Component
@AllArgsConstructor
public class WebSocketEventListener {

    private final SimpUserRegistry simpUserRegistry;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WebSocketService service;

    @EventListener
    public void handleSessionSubscribe(SessionSubscribeEvent event) {
        Message<byte[]> message = event.getMessage();
        String destination = service.extractDestination(message);
        if (destination.equals("/global")) {
            String userName = event.getUser().getName();
            List<String> simpUsers = service.getSimpUsers(simpUserRegistry.getUsers());
            simpMessagingTemplate.convertAndSend(destination,
                    new SystemMessage<List<String>>("SYSTEM-JOIN", userName, simpUsers));
        }
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        System.out.println("DISCONECTED");
        String userName = event.getUser().getName();
        simpMessagingTemplate.convertAndSend("/global",
                new SystemMessage<String>("SYSTEM-LEAVE", "SYSTEM-LEAVE", userName));
    }
}
