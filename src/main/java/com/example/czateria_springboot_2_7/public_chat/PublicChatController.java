package com.example.czateria_springboot_2_7.public_chat;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public-chat")
@AllArgsConstructor
public class PublicChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/global")
    @SendTo("/global")
    PublicChatMessage sendPublicChatMessage(PublicChatMessage message) {
        return message;
    }

    @MessageMapping("/global/{path}")
    void sendPublicCustomChatMessage(@DestinationVariable String path, PublicChatMessage message) {
        String dest = "/global/" + path;
        simpMessagingTemplate.convertAndSend(dest, message);
    }

    @MessageMapping("/priv")
    void sendPrivateMessage(PrivateMessage message) {
        simpMessagingTemplate.convertAndSendToUser(
                message.getReceiverStompName(),
                "/priv",
                message
        );
    }
}