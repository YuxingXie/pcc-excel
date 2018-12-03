package com.lingyun.projects.install.pccexcel.components;

import javax.swing.*;
import java.awt.*;

public abstract class TopFramePanel extends JPanel {
    public TopFramePanel() {
        setLayout(new BorderLayout());
    }
    public abstract void loadData();
    public void reload(){
        this.removeAll();
        loadData();
        this.validate();
        this.repaint();

    }
}
