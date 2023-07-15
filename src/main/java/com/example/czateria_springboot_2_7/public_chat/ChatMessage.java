package com.example.czateria_springboot_2_7.public_chat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public abstract class ChatMessage<T> {
    private Type type;
    private String senderName;
    private String senderStompName;
    private T message;
    private String time;

    public ChatMessage(Type type, String senderName, String senderStompName, T message) {
        this.type = type;
        this.senderName = senderName;
        this.senderStompName = senderStompName;
        this.message = message;
        this.time = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC).toString();
    }

    enum Type {
        MESSAGE, SYSTEM
    }
}
