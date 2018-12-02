package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import com.lingyun.projects.install.pccexcel.route.JPanelRouter;
import com.lingyun.projects.install.pccexcel.route.Observable;
import com.lingyun.projects.install.pccexcel.route.Publisher;

import javax.swing.*;

import java.awt.*;

public class HomeFrame extends BasicFrame {
    private JPanelRouter observer;
    private ExcelPanel excelPanel;
    private PersonPanel personPanel;
    private ExcelExportReviewPanel excelExportReviewPanel;
    private GroupManagerPanel groupManagerPanel;
    private JMenuBar menuBar;
    private ExcelDataRepository excelDataRepository;
    private PersonGroupRepository personGroupRepository;
    private ExcelService excelService;
    private PersonRepository personRepository;
    public HomeFrame(JPanelRouter observer,ExcelService excelService,
                     PersonGroupRepository personGroupRepository,
                     ExcelDataRepository excelDataRepository,
                     PersonRepository personRepository) {
        this.observer = observer;
        this.excelDataRepository=excelDataRepository;
        this.personGroupRepository=personGroupRepository;
        this.excelService=excelService;
        this.personRepository=personRepository;
        this.excelExportReviewPanel=new ExcelExportReviewPanel(this.excelDataRepository);
        personPanel=new PersonPanel(this.personRepository,this.personGroupRepository);
        groupManagerPanel=new GroupManagerPanel(this.personGroupRepository);
        this.excelPanel = new ExcelPanel(this.excelService,this.excelDataRepository,this.observer);
        add(this.excelPanel, BorderLayout.CENTER);
        this.menuBar = new JMenuBar();
        setJMenuBar(this.menuBar);
        addMenuBarItems();
        this.observer.setContainer(this);
        this.observer.addRouterPoint("excelPanel",this.excelPanel);
        this.observer.addRouterPoint("personPanel",this.personPanel);
        this.observer.addRouterPoint("groupManagerPanel",this.groupManagerPanel);
        this.observer.addRouterPoint("excelExportReviewPanel",this.excelExportReviewPanel);

    }
    private void showPersonPanel(){

        add(this.personPanel,BorderLayout.CENTER);


    }
    private void showExcelPanel(){

        add(this.excelPanel,BorderLayout.CENTER);
    }
    private void addMenuBarItems() {

        JMenu menuFile = new JMenu("文件");
        menuBar.add(menuFile);
        JMenuItem menuItem1 = new JMenuItem("当前excel");
        JMenuItem menuItem2 = new JMenuItem("最近打开...");
        JMenuItem menuItem3 = new JMenuItem("退出");

        menuItem1.addActionListener(e -> {
//            HomeFrame.this.excelPanel.openFileChooser(e,HomeFrame.this.excelPanel);
            observer.navigateTo("excelPanel");
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
        menuHelp.add(menuItemHelp1);
        menuHelp.add(menuItemHelp2);
        this.menuBar.add(menuHelp);
    }

}
