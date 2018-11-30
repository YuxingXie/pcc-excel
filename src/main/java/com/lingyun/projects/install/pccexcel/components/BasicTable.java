package com.lingyun.projects.install.pccexcel.components;

import javax.swing.*;
import java.awt.*;

public class BasicTable extends JTable {
    public BasicTable() {
        super();
        this.setGridColor(new Color(235,235,235));
        this.setBackground(new Color(246,246,246));
        this.getTableHeader().setPreferredSize(new Dimension(100,24));
        this.getTableHeader().setReorderingAllowed(false);//不可拖动整列
        setRowHeight(23);
    }
}