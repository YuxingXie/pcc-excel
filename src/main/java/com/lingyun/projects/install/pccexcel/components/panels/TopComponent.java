package com.lingyun.projects.install.pccexcel.components.panels;

import javax.swing.*;
import java.awt.*;

public abstract class TopComponent extends JComponent {
    public TopComponent() {
        setLayout(new BorderLayout());

    }
    public abstract void loadData();
    public void reload(){
        this.removeAll();
        loadData();
        this.validate();
        this.repaint();

    }
    public String routerName(){
        String routerName= this.getClass().getSimpleName();
        return routerName;
    }
}
