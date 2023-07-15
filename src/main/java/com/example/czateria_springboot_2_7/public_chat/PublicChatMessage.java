package com.example.czateria_springboot_2_7.public_chat;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PublicChatMessage extends ChatMessage<String> {

    public PublicChatMessage(String senderName, String senderStompName, String message) {
        super(Type.MESSAGE, senderName, senderStompName, message);
    }

    public PublicChatMessage(String type, String senderName, String senderStompName, String message) {
        super(Type.valueOf(type), senderName, senderStompName, message);
    }
}
