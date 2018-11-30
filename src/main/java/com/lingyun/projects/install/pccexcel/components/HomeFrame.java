package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends BasicFrame {
    private ExcelPanel excelParentPanel;
    private PersonPanel personPanel;
    private JMenuBar menuBar;
    private ExcelDataRepository excelDataRepository;
    private PersonGroupRepository personGroupRepository;
    private ExcelService excelService;
    private PersonRepository personRepository;
    public HomeFrame(ExcelService excelService,
                     PersonGroupRepository personGroupRepository,
                     ExcelDataRepository excelDataRepository,
                     PersonRepository personRepository) {
        this.excelDataRepository=excelDataRepository;
        this.personGroupRepository=personGroupRepository;
        this.excelService=excelService;
        this.personRepository=personRepository;
        personPanel=new PersonPanel(this.personRepository,this.personGroupRepository);
        this.excelParentPanel = new ExcelPanel(this.excelService,this.personGroupRepository,this.excelDataRepository);
        this.menuBar = new JMenuBar();
        setJMenuBar(this.menuBar);
        addMenuBarItems();
        add(this.excelParentPanel, BorderLayout.CENTER);
    }
    private void showPersonPanel(){
        remove(excelParentPanel);
        add(personPanel,BorderLayout.CENTER);
    }
    private void showExcelPanel(){

        remove(personPanel);
        add(excelParentPanel,BorderLayout.CENTER);
    }
    private void addMenuBarItems() {

        JMenu menuFile = new JMenu("文件");
        menuBar.add(menuFile);
        JMenuItem menuItem1 = new JMenuItem("导入excel");
        JMenuItem menuItem2 = new JMenuItem("最近打开...");
        JMenuItem menuItem3 = new JMenuItem("退出");

        menuItem1.addActionListener(e -> {HomeFrame.this.excelParentPanel.openFileChooser(e);});
        menuItem3.addActionListener(e -> System.exit(0));
        menuFile.add(menuItem1);
        menuFile.add(menuItem2);
        menuFile.add(menuItem3);
        JMenu menuSetting = new JMenu("设置");

        JMenuItem addGroupMenuItem=new JMenuItem("分组设置");
        addGroupMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeFrame.this.showExcelPanel();
                HomeFrame.this.excelParentPanel.renderGroupManagerPanel();

            }
        });
        menuSetting.add(addGroupMenuItem);

        JMenuItem personGroupMenuItem=new JMenuItem("人员设置");
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
