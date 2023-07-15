package com.example.czateria_springboot_2_7.public_chat;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PrivateMessage extends ChatMessage<String>{

    private String receiverStompName;

    public PrivateMessage(String senderName, String senderStompName, String message, String receiverStompName) {
        super(Type.MESSAGE, senderName, senderStompName, message);
        this.receiverStompName = receiverStompName;
    }

    public String getReceiverStompName() {
        return receiverStompName;
    }

    public void setReceiverStompName(String receiverStompName) {
        this.receiverStompName = receiverStompName;
    }
}
