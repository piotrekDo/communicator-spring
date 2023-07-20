package com.example.czateria_springboot_2_7.web_socket;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WebSocketService {
    private class Record {
        private String name;
        private Set<String> destinations;

        public Record(String name, Set<String> destinations) {
            this.name = name;
            this.destinations = destinations;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<String> getDestinations() {
            return destinations;
        }

        public void setDestinations(Set<String> destinations) {
            this.destinations = destinations;
        }
    }

    private Record mapSimpUserToRecord(SimpUser simpUser) {
        String name = Objects.requireNonNull(simpUser.getPrincipal()).getName();
        Set<String> destinations = simpUser.getSessions().stream()
                .map(SimpSession::getSubscriptions)
                .flatMap(Collection::stream)
                .map(SimpSubscription::getDestination)
                .collect(Collectors.toSet());
        return new Record(name, destinations);
    }

    String extractDestination(Message<byte[]> message) {
        return (String) message.getHeaders().get("simpDestination");
    }

    List<String> getSimpUsers(Set<SimpUser> simpUsers) {
        return simpUsers.stream()
                .map(u -> Objects.requireNonNull(u.getPrincipal()).getName())
                .collect(Collectors.toList());
    }

    List<String> getSimpUsersByDestination(Set<SimpUser> simpUsers, String destination) {
        List<Record> usersWithDestinations = simpUsers.stream()
                .map(this::mapSimpUserToRecord)
                .toList();

        return usersWithDestinations.stream()
                .filter(u -> u.destinations.contains(destination))
                .map(Record::getName)
                .collect(Collectors.toList());
    }
}
