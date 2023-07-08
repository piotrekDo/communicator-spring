package com.example.czateria_springboot_2_7.public_chat;

import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
public class PrivateMessage {

    private String senderName;
    private String senderStompName;
    private String receiverStompName;
    private String message;
    private String time;

    public PrivateMessage(String senderName, String senderStompName, String receiverStompName, String message) {
        this.senderName = senderName;
        this.senderStompName = senderStompName;
        this.receiverStompName = receiverStompName;
        this.message = message;
        this.time = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC).toString();
    }
}
