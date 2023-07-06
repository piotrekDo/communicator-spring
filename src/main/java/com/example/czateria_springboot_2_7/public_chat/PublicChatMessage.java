package com.example.czateria_springboot_2_7.public_chat;

import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
public class PublicChatMessage {
    private String senderName;
    private String senderStompName;
    private String message;
    private String time;

    public PublicChatMessage(String senderName, String senderStompName, String message) {
        this.senderName = senderName;
        this.senderStompName = senderStompName;
        this.message = message;
        this.time = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC).toString();
    }
}
