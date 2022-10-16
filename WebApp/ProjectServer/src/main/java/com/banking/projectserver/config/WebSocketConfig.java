package com.banking.projectserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * TODO:此处写WebSocketConfig类的描述
 *
 * @author 孟繁霖
 * @date 2021/12/12 16:59
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //注册一个Stomp的节点（endpoint）,并指定使用SockJS协议，配置客户端尝试连接地址/endpointWisely
        stompEndpointRegistry.addEndpoint("endPoint").setAllowedOriginPatterns("**").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //服务端发送消息给客户端的域,多个用逗号隔开
        registry.enableSimpleBroker("/topic", "/user");
        //定义一对一推送的时候前缀（点对点/user）
        registry.setUserDestinationPrefix("/user");
        //定义websoket前缀（/ws）
        registry.setApplicationDestinationPrefixes("/ws");
    }

}
