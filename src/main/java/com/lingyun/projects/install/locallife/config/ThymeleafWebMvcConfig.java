package com.lingyun.projects.install.locallife.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.resourceresolver.UrlResourceResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

@Configuration
public class ThymeleafWebMvcConfig {
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//
//        LayoutDialect layoutDialect=new LayoutDialect();
//        templateEngine.addDialect(layoutDialect);
//
//        UrlTemplateResolver urlTemplateResolver=new UrlTemplateResolver();
//        urlTemplateResolver.setResourceResolver(new UrlResourceResolver());
//        templateEngine.addTemplateResolver(urlTemplateResolver);
//        return templateEngine;
//    }
//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver(){
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//
//        return  resolver;
//    }
}
