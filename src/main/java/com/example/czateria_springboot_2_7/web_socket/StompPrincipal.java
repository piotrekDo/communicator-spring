package com.example.czateria_springboot_2_7.web_socket;

import lombok.Data;
import lombok.ToString;

import java.security.Principal;

@ToString
@Data
public class StompPrincipal implements Principal {
    String stompUserName;
    String userName;

    public StompPrincipal(String stompUserName) {
        this.stompUserName = stompUserName;
    }

    public String getName() {
        return this.stompUserName;
    }
}