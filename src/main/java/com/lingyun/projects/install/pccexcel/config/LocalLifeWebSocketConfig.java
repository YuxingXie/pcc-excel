package com.lingyun.projects.install.pccexcel.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


@Configuration
@EnableWebSocketMessageBroker
public class LocalLifeWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(LocalLifeWebSocketConfig.class);




    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("ws_"+this.projectSmswall.getName());
    }

    /**
     * 注册服务器的连接端点
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String endpoint="/ws-local-life";
        registry.addEndpoint(endpoint).setAllowedOrigins("*");
    }


}