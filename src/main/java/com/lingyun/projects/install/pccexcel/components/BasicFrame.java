package com.lingyun.projects.install.pccexcel.components;

import javax.swing.*;
import java.awt.*;

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
        setIconImage(new ImageIcon(ClassLoader.getSystemResource("images/icon/icon.png")).getImage());
        setLocationByPlatform(true);
    }
}
