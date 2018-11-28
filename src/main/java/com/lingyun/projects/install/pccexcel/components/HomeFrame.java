package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.file.OLE2OfficeExcelUtils;
import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class HomeFrame extends JFrame {

    private JPanel jPanelCenter;
    private JFileChooser excelFileChooser;
    private JTextField titleTextField;
    private JTabbedPane excelDataPanel;
    private ExcelService excelService;
    private JButton importExcelBtn;
    @Resource private ExcelRepository excelRepository;
    public HomeFrame(JPanel jPanelCenter, JFileChooser excelFileChooser,JTextField titleTextField,JTabbedPane excelDataPanel,ExcelService excelService,JButton importExcelBtn) {
        this.jPanelCenter = jPanelCenter;
        this.excelFileChooser = excelFileChooser;
        this.titleTextField = titleTextField;
        this.excelDataPanel=excelDataPanel;
        this.excelService=excelService;
        this.importExcelBtn=importExcelBtn;
        setLayout(new BorderLayout(10, 10));
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("宁乡市政协excel工具");
        setIconImage(new ImageIcon(ClassLoader.getSystemResource("images/icon/icon.png")).getImage());
        setLocationByPlatform(true);

        add(this.jPanelCenter, BorderLayout.CENTER);
        this.titleTextField.setText("当前文件: "+(Constant.currentExcel==null?"没有选择excel文件":Constant.currentExcel.getPath()));
        this.jPanelCenter.add(this.excelDataPanel);
        addBottomButtonGroup();
        JLabel label=new JLabel("双击单元格修改数据且不会覆盖原始文件，点击\"保存\"后会导出到excel中；点击\"刷新\"可以重新载入excel内容。");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.jPanelCenter.add(label,BorderLayout.NORTH);
        addMenuBar();

        this.importExcelBtn.addActionListener(e -> {


            openFileChooser(e);

        });
    }

    private void redrawExcelDataPanel() {
        HomeFrame.this.excelDataPanel=ComponentsDrawTools.drawTabbedPaneByExcel(Constant.currentExcel,HomeFrame.this.excelDataPanel);
        HomeFrame.this.excelDataPanel.revalidate();
        HomeFrame.this.excelDataPanel.repaint();
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menuFile = new JMenu("文件");
        menuBar.add(menuFile);
        JMenuItem menuItem1 = new JMenuItem("导入excel");
        JMenuItem menuItem2 = new JMenuItem("最近打开...");
        JMenuItem menuItem3 = new JMenuItem("退出");

        menuItem1.addActionListener(e -> {openFileChooser(e);});
        menuItem3.addActionListener(e -> System.exit(0));
        menuFile.add(menuItem1);
        menuFile.add(menuItem2);
        menuFile.add(menuItem3);
        JMenu menuSetting = new JMenu("设置");
        JMenuItem menuItemPersonGroup=new JMenuItem("人员分组");
        menuSetting.add(menuItemPersonGroup);
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

    private void openFileChooser(ActionEvent e) {
        System.out.println("e.getSource():"+e.getSource());
        HomeFrame.this.jPanelCenter.add(HomeFrame.this.excelFileChooser, BorderLayout.CENTER);

        int result = HomeFrame.this.excelFileChooser.showOpenDialog(HomeFrame.this.jPanelCenter);
        System.out.println(result);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = HomeFrame.this.excelFileChooser.getSelectedFile();
            Excel excel = HomeFrame.this.excelService.findByFilePath(file.getAbsolutePath());
            String json= null;
            try {
                json = BeanUtil.javaToJson(OLE2OfficeExcelUtils.getData(file));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if(excel==null){
                excel=new Excel();
                excel.setPath(file.getAbsolutePath());

            }
            excel.setLastOpenDate(new Date());
            excel.setDataJson(json==null?null:json.getBytes(StandardCharsets.UTF_8));
            System.out.println(new String(excel.getDataJson(),StandardCharsets.UTF_8));
            excel=excelRepository.save(excel);
            Constant.currentExcel=excel;
            HomeFrame.this.titleTextField.setText("当前文件: " +excel.getPath());
            redrawExcelDataPanel();
            HomeFrame.this.titleTextField.revalidate();
            HomeFrame.this.titleTextField.repaint();
            HomeFrame.this.jPanelCenter.revalidate();
            HomeFrame.this.jPanelCenter.repaint();
        }
    }

    private void addBottomButtonGroup() {
        JPanel buttonGroupPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton=new JButton("保存");
        saveButton.addActionListener(e -> {
            JOptionPane.showConfirmDialog(HomeFrame.this.excelDataPanel,"当前没有excel文件，请先导入文件。","提示",JOptionPane.OK_OPTION);

        });
        buttonGroupPanel.add(saveButton);
        JButton groupBtn=new JButton("分组预览");
        groupBtn.addActionListener(e -> {
            if(Constant.currentExcel==null){
                int selection=JOptionPane.showConfirmDialog(HomeFrame.this.excelDataPanel,"当前没有excel文件，请先导入文件。","提示",JOptionPane.OK_OPTION);
                if(selection==JOptionPane.OK_OPTION){
                    openFileChooser(e);
                }
            }
        });
        buttonGroupPanel.add(groupBtn);
        buttonGroupPanel.add(new JButton("刷新"));


        this.jPanelCenter.add(buttonGroupPanel,BorderLayout.SOUTH);
    }
}
