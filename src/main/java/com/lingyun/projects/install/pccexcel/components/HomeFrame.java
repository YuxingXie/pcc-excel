package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.projects.install.pccexcel.config.Constant;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HomeFrame extends JFrame {

    private JPanel jPanelCenter;
    private JFileChooser excelFileChooser;
    private JTextField titleTextField;
    public HomeFrame(JPanel jPanelCenter, JFileChooser excelFileChooser,JTextField titleTextField) {
        this.jPanelCenter = jPanelCenter;
        this.excelFileChooser = excelFileChooser;
        this.titleTextField = titleTextField;
        setLayout(new BorderLayout(10, 10));
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("宁乡市政协excel工具");
        setIconImage(new ImageIcon("classpath:images/icon/icon.png").getImage());
        setLocationByPlatform(true);
        this.titleTextField.setText("当前未选择文件");
        this.jPanelCenter.add(this.titleTextField,BorderLayout.NORTH);
        add(this.jPanelCenter, BorderLayout.CENTER);

        this.titleTextField.setText("当前文件: "+(Constant.currentExcel==null?"没有选择excel文件":Constant.currentExcel.getAbsolutePath()));


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menuFile = new JMenu("文件");
        menuBar.add(menuFile);
        JMenuItem menuItem1 = new JMenuItem("导入excel");
        JMenuItem menuItem2 = new JMenuItem("导入并导出excel");
        JMenuItem menuItem3 = new JMenuItem("查看当前excel");
        JMenuItem menuItem4 = new JMenuItem("导出excel");
        JMenuItem menuItem5 = new JMenuItem("退出");
        menuItem1.addActionListener(e -> {

            System.out.println("菜单监听.......,action command:"+e.getActionCommand());
            HomeFrame.this.jPanelCenter.add(HomeFrame.this.excelFileChooser, BorderLayout.CENTER);

            int result = HomeFrame.this.excelFileChooser.showOpenDialog(HomeFrame.this.jPanelCenter);
            System.out.println(result);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = HomeFrame.this.excelFileChooser.getSelectedFile();
                if (file != null) {
                    Constant.currentExcel = file;

//                        jPanelCenter.add(new SimpleStringComponent(Constant.currentExcel.getAbsolutePath()),BorderLayout.CENTER);
                }
                HomeFrame.this.titleTextField.setText("当前文件: " + (Constant.currentExcel == null ? "没有选择excel文件" : Constant.currentExcel.getAbsolutePath()));
                HomeFrame.this.titleTextField.revalidate();
                HomeFrame.this.titleTextField.repaint();
                HomeFrame.this.jPanelCenter.revalidate();
                HomeFrame.this.jPanelCenter.repaint();
            }

        });
        menuItem4.addActionListener(e -> System.exit(0));
        menuFile.add(menuItem1);
        menuFile.add(menuItem2);
        menuFile.add(menuItem3);
        menuFile.add(menuItem4);
        menuFile.add(menuItem5);
        JMenu menuSetting = new JMenu("设置");
        menuBar.add(menuSetting);
        JMenu menuHistory = new JMenu("历史记录");
        menuBar.add(menuHistory);
        JMenu menuHelp = new JMenu("帮助");
        JMenuItem menuItemHelp1 = new JMenuItem("分组依据");
        JMenuItem menuItemHelp2 = new JMenuItem("查看日志");
        menuHelp.add(menuItemHelp1);
        menuHelp.add(menuItemHelp2);
        menuBar.add(menuHelp);
    }
}
