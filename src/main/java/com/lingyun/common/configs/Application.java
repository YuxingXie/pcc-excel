package com.lingyun.common.configs;


import com.lingyun.projects.install.pccexcel.components.HomeFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.swing.*;
import java.io.IOException;


@SpringBootApplication
        (scanBasePackages = {
        "com.lingyun.projects.install.**.controller",
//        "com.lingyun.projects.install.**.service",
        "com.lingyun.projects.install.**.config",
        "com.lingyun.common.annotation.controller",
        "com.lingyun.common.configs"
        })
//@EnableJpaRepositories(basePackages = {"com.lingyun.projects.install.**.repo"})
@EntityScan(basePackages ={"com.lingyun.projects.install.**.entity"})
public class Application implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) throws IOException {


        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).headless(false).run(args);
        HomeFrame appFrame = context.getBean(HomeFrame.class);
        appFrame.setVisible(true);

    }

    @Bean
    public HomeFrame simpleFrame(JPanel jPanelCenter,JFileChooser excelFileChooser,JTextField titleTextField) {

        return new HomeFrame(jPanelCenter, excelFileChooser,titleTextField);
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }

}