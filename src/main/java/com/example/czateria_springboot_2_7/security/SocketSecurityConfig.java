package com.example.czateria_springboot_2_7.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.*;

@Configuration
public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.simpTypeMatchers(CONNECT).authenticated();
        messages.simpTypeMatchers(UNSUBSCRIBE, DISCONNECT).permitAll();
        messages.simpDestMatchers("/global").hasAnyAuthority("USER");
        messages.simpDestMatchers("/user/priv").hasAnyAuthority("USER");
        messages.anyMessage().authenticated();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
