package com.happiday.Happi_Day.config;

import com.happiday.Happi_Day.domain.entity.user.CustomUserDetails;
import com.happiday.Happi_Day.domain.service.JpaUserDetailsManager;
import com.happiday.Happi_Day.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtTokenUtils jwtTokenUtils;
    private final JpaUserDetailsManager jpaUserDetailsManager;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws");
//                .setAllowedOriginPatterns("*")
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub", "/sub");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor
                        = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authToken = accessor.getNativeHeader("token");
                    if (authToken != null && authToken.size() == 1){
                        String username = jwtTokenUtils.parseClaims(authToken.get(0))
                                .getSubject();
                       UserDetails userDetails = jpaUserDetailsManager.loadUserByUsername(username);
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                authToken,
                                userDetails.getAuthorities()
                        );
                        accessor.setUser(authentication);
                    } else throw new AccessDeniedException("invalid token");
                }
                else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    try {
                        CustomUserDetails jwtUserDetails = (CustomUserDetails) ((Authentication) accessor.getUser()).getPrincipal();
                        if (!accessor.getDestination().endsWith(String.format("/%d", jwtUserDetails.getId())))
                            throw new AccessDeniedException("forbidden");
                    } catch (ClassCastException | NullPointerException e) {
                        log.error(e.getMessage());
                        throw new AccessDeniedException("invalid credentials");
                    }
                }
                return message;
            }
        });
    }
}
