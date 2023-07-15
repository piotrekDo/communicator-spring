package com.example.czateria_springboot_2_7.public_chat;

public class SystemMessage<T> extends ChatMessage<T> {

    public SystemMessage(String senderName, String senderStompName, T message) {
        super(Type.SYSTEM, senderName, senderStompName, message);
    }
}
