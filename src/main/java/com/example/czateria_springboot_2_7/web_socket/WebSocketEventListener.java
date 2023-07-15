package com.example.czateria_springboot_2_7.web_socket;

import com.example.czateria_springboot_2_7.public_chat.SystemMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;
import java.util.Set;

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
            Set<SimpUser> simpUsers = simpUserRegistry.getUsers();
            List<String> currentUsers = service.getSimpUsers(simpUsers);
            simpMessagingTemplate.convertAndSend(destination,
                    new SystemMessage<List<String>>("SYSTEM-JOIN", "SYSTEM-JOIN", currentUsers));
        }
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        System.out.println("----------------------------");
        System.out.println("DISCONECTED");
        System.out.println(event.getClass());
        System.out.println(event);
        System.out.println(event.getUser());
        System.out.println(event.getMessage().getHeaders());

        System.out.println("----------------------------");
    }
}
