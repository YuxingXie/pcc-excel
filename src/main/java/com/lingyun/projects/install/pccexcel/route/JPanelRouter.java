package com.lingyun.projects.install.pccexcel.route;

import com.lingyun.projects.install.pccexcel.components.TopFramePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;

public class JPanelRouter extends BasicRooter<TopFramePanel>{

    public void setContainer(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    private Container container;
    @Override
    public void show(TopFramePanel jPanel) {
        jPanel.reload();
        jPanel.setVisible(true);
        jPanel.validate();
        jPanel.repaint();
        if(container!=null){
            if (Arrays.asList(container.getComponents()).contains(jPanel)){
            }else {
                container.add(jPanel);
            }
        }
    }

    @Override
    public void hide(TopFramePanel jPanel) {
        jPanel.setVisible(false);
        if(container!=null){
            if (Arrays.asList(container.getComponents()).contains(jPanel)){
                container.remove(jPanel);
            }
        }
    }
}
