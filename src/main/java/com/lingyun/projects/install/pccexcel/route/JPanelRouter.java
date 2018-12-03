package com.lingyun.projects.install.pccexcel.route;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.projects.install.pccexcel.components.panels.TopComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JPanelRouter extends BasicRooter<TopComponent>{


    private List<TopComponent> alwaysRefreshComponents=new ArrayList<>();
    public void setContainer(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    private Container container;
    @Override
    public void show(TopComponent component) {
        refreshAlways();
        component.reload();
        component.setVisible(true);

        if(container!=null){
            if (Arrays.asList(container.getComponents()).contains(component)){
            }else {
                container.add(component);
            }
        }
    }

    @Override
    public void hide(TopComponent component) {
        component.setVisible(false);
        if(container!=null){
            if (Arrays.asList(container.getComponents()).contains(component)){
                container.remove(component);
            }
        }

    }
    private void refreshAlways(){
        if(BeanUtil.emptyCollection(alwaysRefreshComponents)) return;
        for(TopComponent component:alwaysRefreshComponents){
            System.out.println("refresh always component:"+component.getClass().getSimpleName());
            component.loadData();
        }
    }
    public void addAlwaysRefreshComponent(TopComponent topComponent){
        alwaysRefreshComponents.add(topComponent);
    }
}
