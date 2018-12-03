package com.lingyun.projects.install.pccexcel.route;

import com.lingyun.projects.install.pccexcel.components.basic.BasicFrame;
import com.lingyun.projects.install.pccexcel.components.panels.TopComponent;

import javax.swing.*;
import java.awt.*;

public class RouterTest  {
    public static void main(String[] args){
        JFrame frame =new BasicFrame();
        frame.setVisible(true);
        TopComponent routerPanel1=new TopComponent() {
            @Override
            public void loadData() {

            }
        };
        TopComponent routerPanel2= new TopComponent() {
            @Override
            public void loadData() {

            }
        };
        routerPanel2.add(new JLabel("panel 2"));
        routerPanel2.setVisible(false);
        JLabel label=new JLabel("text");
        JMenuBar menuBar=new JMenuBar();
        JMenu menu=new JMenu("navigate");
        JMenuItem forward=new JMenuItem("forward");
        JMenuItem back=new JMenuItem("back");
        JMenuItem to=new JMenuItem("to p1");
        menu.add(forward);
        menu.add(back);
        menu.add(to);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);


        Publisher publisher=new Publisher();
        Observable  source$=new Observable(publisher);
        JPanelRouter observer= new JPanelRouter();
        observer.setContainer(frame);
        observer.addRouterPoint("p1",routerPanel1);
        observer.addRouterPoint("p2",routerPanel2);
        source$.onSubscribe(observer);

        forward.addActionListener(e -> observer.forward());
        back.addActionListener(e -> observer.back());
        to.addActionListener(e -> observer.navigateTo("p1"));
        routerPanel1.add(label,BorderLayout.CENTER);
        frame.add(routerPanel1);
//        frame.add(routerPanel2);
    }
}
