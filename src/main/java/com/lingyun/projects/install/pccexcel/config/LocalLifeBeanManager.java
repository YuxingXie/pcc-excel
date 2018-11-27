package com.lingyun.projects.install.pccexcel.config;

import com.lingyun.common.annotation.BeanManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@Configuration
public class LocalLifeBeanManager extends BeanManager {
    public static SimpMessagingTemplate brokerMessagingTemplate;

    public LocalLifeBeanManager(SimpMessagingTemplate brokerMessagingTemplate) {
        LocalLifeBeanManager.brokerMessagingTemplate=brokerMessagingTemplate;
    }


}
