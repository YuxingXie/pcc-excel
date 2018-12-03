package com.lingyun.projects.install.pccexcel.components.basic;

import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BasicFrame extends JFrame {
    public BasicFrame() {
        setLayout(new BorderLayout(10, 10));
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("宁乡市政协excel工具");
        try {
            setIconImage(new ImageIcon(new ClassPathResource("/images/icon/icon.png").getURL()).getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }


        setLocationByPlatform(true);
    }
}
