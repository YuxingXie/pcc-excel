package com.lingyun.projects.install.pccexcel.components.frames;

import com.lingyun.projects.install.pccexcel.components.TextComponent;
import com.lingyun.projects.install.pccexcel.components.basic.BasicFrame;
import com.lingyun.projects.install.pccexcel.components.panels.*;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import com.lingyun.projects.install.pccexcel.route.JPanelRouter;
import org.springframework.core.io.ClassPathResource;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HomeFrame extends BasicFrame {
    private JPanelRouter observer;
    private ExcelPanel excelPanel;
    private PersonPanel personPanel;
    private ExcelExportReviewPanel excelExportReviewPanel;
    private GroupManagerPanel groupManagerPanel;
    private JMenuBar menuBar;
    LeftMenuTreeComponent menuTree;
    private TextComponent consolePanel;
    public HomeFrame(JPanelRouter observer,ExcelService excelService,
                     PersonGroupRepository personGroupRepository,
                     ExcelDataRepository excelDataRepository,
                     JTextField consoleTextField,
                     PersonRepository personRepository,Excel excel) {


        this.observer = observer;

        this.excelExportReviewPanel=new ExcelExportReviewPanel(excelDataRepository,excelService);
        personPanel=new PersonPanel(personRepository,personGroupRepository);
        groupManagerPanel=new GroupManagerPanel(personGroupRepository);
        menuTree =new LeftMenuTreeComponent(excelService,excelDataRepository,this.observer);
        this.excelPanel = new ExcelPanel(menuTree,excelService,this.observer);
        this.consolePanel = new TextComponent(consoleTextField);

//        this.observer.addAlwaysRefreshComponent(menuTree);

        this.menuBar = new JMenuBar();
        setJMenuBar(this.menuBar);
        addMenuBarItems();
        this.observer.setContainer(this);
        registerRouter();




//        addToolBar();


        add(this.excelPanel, BorderLayout.CENTER);
//        add(this.leftMenuTree, BorderLayout.WEST);
        add(menuTree, BorderLayout.WEST);
    }

    private void registerRouter() {
        this.observer.addRouterPoint("excelPanel",this.excelPanel);
        this.observer.addRouterPoint("personPanel",this.personPanel);
        this.observer.addRouterPoint("groupManagerPanel",this.groupManagerPanel);
        this.observer.addRouterPoint("excelExportReviewPanel",this.excelExportReviewPanel);
        this.observer.addRouterPoint("consolePanel", consolePanel);
    }

    private void addMenuBarItems() {

        JMenu menuFile = new JMenu("文件");
        menuBar.add(menuFile);
        JMenuItem menuItem1 = new JMenuItem("当前excel");
        JMenuItem menuItem2 = new JMenuItem("导出预览");
        JMenuItem menuItem3 = new JMenuItem("退出");

        menuItem1.addActionListener(e -> {
//            HomeFrame.this.excelPanel.openFileChooser(e,HomeFrame.this.excelPanel);
            observer.navigateTo("excelPanel");
        });
        menuItem2.addActionListener(e -> {
            observer.navigateTo("excelExportReviewPanel");
        });
        menuItem3.addActionListener(e -> System.exit(0));
        menuFile.add(menuItem1);
        menuFile.add(menuItem2);
        menuFile.add(menuItem3);
        JMenu menuSetting = new JMenu("设置");

        JMenuItem addGroupMenuItem=new JMenuItem("分组设置");
        addGroupMenuItem.addActionListener(e -> {
//                HomeFrame.this.showPersonPanel();
            observer.navigateTo("groupManagerPanel");
        });
        menuSetting.add(addGroupMenuItem);

        JMenuItem personGroupMenuItem=new JMenuItem("人员设置");
        personGroupMenuItem.addActionListener(e -> observer.navigateTo("personPanel"));
        menuSetting.add(personGroupMenuItem);

        this.menuBar.add(menuSetting);
        JMenu menuHistory = new JMenu("历史记录");
        this.menuBar.add(menuHistory);
        JMenu menuHelp = new JMenu("帮助");
        JMenuItem menuItemHelp1 = new JMenuItem("分组依据");
        JMenuItem menuItemHelp2 = new JMenuItem("查看日志");
        menuItemHelp2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                observer.navigateTo("textComponent");
            }
        });
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        menuHelp.add(menuItemHelp1);
        menuHelp.add(menuItemHelp2);
        menuHelp.add(aboutMenuItem);
        this.menuBar.add(menuHelp);
    }

}
