package com.example.czateria_springboot_2_7.web_socket;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WebSocketService {

    String extractDestination(Message<byte[]> message) {
        return (String) message.getHeaders().get("simpDestination");
    }

    List<String> getSimpUsers(Set<SimpUser> simpUsers) {
        return simpUsers.stream()
                .map(u -> Objects.requireNonNull(u.getPrincipal()).getName())
                .collect(Collectors.toList());
    }
}
