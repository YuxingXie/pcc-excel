package com.lingyun.projects.install.pccexcel.route;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;

public class JPanelRouter extends BasicRooter<JPanel>{

    public void setContainer(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    private Container container;
    @Override
    public void show(JPanel jPanel) {
        jPanel.setVisible(true);
        if(container!=null){
            if (Arrays.asList(container.getComponents()).contains(jPanel)){
            }else {
                container.add(jPanel);
            }
        }
    }

    @Override
    public void hide(JPanel jPanel) {
        jPanel.setVisible(false);
        if(container!=null){
            if (Arrays.asList(container.getComponents()).contains(jPanel)){
                container.remove(jPanel);
            }
        }
    }
}
