package com.lingyun.common.configs;


import com.lingyun.common.support.data.ServerProject;
import com.lingyun.common.support.exception.BusinessException;
import com.lingyun.common.support.util.clazz.BeanUtil;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.MultipartConfigElement;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;



@SpringBootApplication
        (scanBasePackages = {
        "com.lingyun.projects.install.**.controller",
        "com.lingyun.projects.install.**.service",
        "com.lingyun.projects.install.**.config",
        "com.lingyun.common.annotation.controller",
        "com.lingyun.common.configs"
        })
@EnableJpaRepositories(basePackages = {"com.lingyun.projects.install.**.repo"})
@EntityScan(basePackages ={"com.lingyun.projects.install.**.entity"})
public class Application implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) throws IOException {

//        makeLogFileDir();
//        SpringApplication.run(Application.class, args);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建及设置窗口
        JFrame frame = new JFrame("宁乡市政协excel处理工具");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加 "Hello World" 标签
        JLabel label = new JLabel("宁乡市政协");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        frame.getContentPane().add(label);
        JButton button=new JButton("开始使用");
        button.setBackground(Color.BLACK);
        frame.add(button);

        // 显示窗口
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1024,768);

    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


    }

}