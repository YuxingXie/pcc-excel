package com.lingyun.projects.install.locallife.config;

import com.lingyun.common.annotation.WebMvcConfig;
import com.lingyun.common.support.data.ServerProject;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class LocalLifeResourceConfig extends WebMvcConfig {
    private ServerProject projectLocalLife;
    public LocalLifeResourceConfig(ServerProject projectLocalLife) {
        this.projectLocalLife=projectLocalLife;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registerProjectResource(registry,projectLocalLife );
    }
}
