package com.example.czateria_springboot_2_7.public_chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public-chat")
public class PublicChatController {

    @MessageMapping("/global")
    @SendTo("/global")
    PublicChatMessage sendPublicChatMessage(PublicChatMessage message) {
        return message;
    }
}
