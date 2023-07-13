package com.example.czateria_springboot_2_7.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

import static com.example.czateria_springboot_2_7.security.EncryptionConfiguration.getAlgorithm;
import static java.util.Arrays.stream;
import static org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.UNSUBSCRIBE;

public class SocketAuthenticationInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("CONFIGURER INBOUND INTERCEPTOR");
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;
        StompCommand command = accessor.getCommand();
        System.out.println(command);
        System.out.println(accessor.getDestination());


        if (command != null && !(command.equals(DISCONNECT) || command.equals(UNSUBSCRIBE))) {
            String authorization = accessor.getFirstNativeHeader("Authorization");
            String token = authorization.substring("Bearer ".length());
            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            accessor.setUser(authenticationToken);
        }
        return message;
    }
}
