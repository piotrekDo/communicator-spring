package com.example.czateria_springboot_2_7.web_socket;

import com.example.czateria_springboot_2_7.security.SocketAuthenticationInterceptor;
import com.example.czateria_springboot_2_7.security.WebSocketHandshakeInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket-connect")
                .setAllowedOrigins("http://localhost:5173")
//                .setHandshakeHandler(new CustomHandshakeHandler())
                .addInterceptors(new WebSocketHandshakeInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/websocket");
        registry.enableSimpleBroker("/global", "/priv");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(
                new SocketAuthenticationInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        WebSocketMessageBrokerConfigurer.super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        WebSocketMessageBrokerConfigurer.super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return WebSocketMessageBrokerConfigurer.super.configureMessageConverters(messageConverters);
    }
}
