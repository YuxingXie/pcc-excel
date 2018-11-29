package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.common.support.util.file.OLE2OfficeExcelUtils;
import com.lingyun.common.support.util.string.StringUtils;
import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFrame extends JFrame {

    private JPanel excelParentPanel;
    private JPanel groupManagerPanel;
    private JFileChooser excelFileChooser;
    private JTextField titleTextField;
    private JTabbedPane excelDataPanel;
    private ExcelService excelService;
    private JButton importExcelBtn;
    private JTable personGroupTable;
    private PersonGroupTableModel personGroupTableModel;
    private JScrollPane personGroupScrollPane;
    private List<PersonGroup> personGroups;
    @Resource private ExcelRepository excelRepository;
    private PersonGroupRepository personGroupRepository;
    public HomeFrame(JPanel excelParentPanel, JPanel groupManagerPanel, JFileChooser excelFileChooser, JTextField titleTextField, JTabbedPane excelDataPanel, ExcelService excelService, JButton importExcelBtn,PersonGroupRepository personGroupRepository) {
        injectComponents(excelParentPanel, groupManagerPanel, excelFileChooser, titleTextField, excelDataPanel, excelService, importExcelBtn,personGroupRepository);
        initFrame();
        addMenuBar();
        addBottomButtonGroup();
        this.importExcelBtn.addActionListener(e -> {
            openFileChooser(e);
        });
    }

    private void initFrame() {
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
        add(this.excelParentPanel, BorderLayout.CENTER);
        this.titleTextField.setText("当前文件: "+(Constant.currentExcel==null?"没有选择excel文件":Constant.currentExcel.getPath()));
        this.excelParentPanel.add(this.excelDataPanel);
        JLabel label=new JLabel("双击单元格修改数据且不会覆盖原始文件，点击\"保存\"后会导出到excel中；点击\"刷新\"可以重新载入excel内容。");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.excelParentPanel.add(label,BorderLayout.NORTH);
        this.personGroups =personGroupRepository.findAll();
        this.personGroupTableModel=new PersonGroupTableModel(ComponentsDrawTools.getRowDataOfPersonGroups(this.personGroups),ComponentsDrawTools.getColumnNamesOfPersonGroups());

        this.personGroupTable=new JTable(this.personGroupTableModel);
//        this.personGroupTable.setBackground(new Color(244, 244, 244));
        this.personGroupTable.setGridColor(new Color(230, 230, 230));
        this.personGroupTable.getTableHeader().setPreferredSize(new Dimension(50, 30));
        this.personGroupScrollPane=new JScrollPane(this.personGroupTable);
//        this.personGroupScrollPane.getViewport().setBackground(Color.LIGHT_GRAY);
    }

    private void injectComponents(JPanel excelParentPanel, JPanel groupManagerPanel,JFileChooser excelFileChooser, JTextField titleTextField, JTabbedPane excelDataPanel, ExcelService excelService, JButton importExcelBtn,PersonGroupRepository personGroupRepository) {
        this.excelParentPanel = excelParentPanel;
        this.groupManagerPanel =groupManagerPanel;
        this.excelFileChooser = excelFileChooser;
        this.titleTextField = titleTextField;
        this.excelDataPanel=excelDataPanel;
        this.excelService=excelService;
        this.importExcelBtn=importExcelBtn;

        this.personGroupRepository=personGroupRepository;
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

        JMenuItem addGroupMenuItem=new JMenuItem("分组设置");
        addGroupMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderGroupManagerPanel();

            }
        });
        menuSetting.add(addGroupMenuItem);

        JMenuItem personGroupMenuItem=new JMenuItem("人员设置");
        menuSetting.add(personGroupMenuItem);

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

    //TODO:Many problems
    public void renderGroupManagerPanel() {
        System.out.println("渲染用户分组组件");
        this.excelParentPanel.setVisible(false);
        this.groupManagerPanel.setVisible(true);
        this.groupManagerPanel.setLayout(new BorderLayout());
        JPanel southPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addBtn=new JButton("添加分组(添加后请点击保存)");
        southPanel.add(addBtn);
        JButton deleteBtn=new JButton("删除选中");
        southPanel.add(deleteBtn);
        JButton saveBtn=new JButton("保存编辑");
        southPanel.add(saveBtn);

        this.groupManagerPanel.add(southPanel,BorderLayout.SOUTH);
        JPanel centerPanel=new JPanel(new BorderLayout());


        if(!BeanUtil.emptyCollection(this.personGroups)) {
//            updateTableModelByPersonGroupList();
//            this.personGroupTable = new JTable(this.personGroupTableModel);

//            this.personGroupTable.setModel(this.personGroupTableModel);
//            this.personGroupTable.getColumnModel().getColumn(0).setCellEditor(null);
           personGroupButtonEventBinding(addBtn, deleteBtn, saveBtn);
        }

        centerPanel.add(this.personGroupScrollPane,BorderLayout.CENTER);
        centerPanel.add(new JLabel("分组列表："),BorderLayout.NORTH);
        this.groupManagerPanel.add(centerPanel,BorderLayout.CENTER);
        add(this.groupManagerPanel);
    }



    private void personGroupButtonEventBinding(JButton addBtn, JButton deleteBtn, JButton saveBtn) {
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<PersonGroup> personGroupsToAdd=new ArrayList<>();
                for(int i=0;i<HomeFrame.this.personGroupTable.getRowCount();i++){
                    PersonGroup personGroup=new PersonGroup();
                    Object _id=HomeFrame.this.personGroupTable.getValueAt(i,0);
                    if(_id!=null&&!_id.toString().trim().equals("")){
                        personGroup.setId(HomeFrame.this.personGroupTable.getValueAt(i,0).toString());
                    }
                    Object groupName=HomeFrame.this.personGroupTable.getValueAt(i,1);
                    if(groupName==null||groupName.toString().trim().equals("")){
                        JOptionPane.showMessageDialog(HomeFrame.this,"第 "+(i+1)+" 行 分组名称不能为空");
                        return;
                    }
                    personGroup.setGroupName(groupName.toString());

                    Object description=HomeFrame.this.personGroupTable.getValueAt(i,2);
                    if(description!=null&&!description.toString().trim().equals("")){
                        personGroup.setDescription(HomeFrame.this.personGroupTable.getValueAt(i,2).toString());
                    }
                    Object createDate=HomeFrame.this.personGroupTable.getValueAt(i,3);
                    if(createDate!=null&&!createDate.toString().trim().equals("")){
                        personGroup.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,HomeFrame.this.personGroupTable.getValueAt(i,3).toString()));
                    }
                    personGroupsToAdd.add(personGroup);
                }

                HomeFrame.this.personGroups=HomeFrame.this.personGroupRepository.save(personGroupsToAdd);
                System.out.println(BeanUtil.javaToJson(HomeFrame.this.personGroups));
                HomeFrame.this.personGroupTableModel.setPersonGroups(HomeFrame.this.personGroups);
//                HomeFrame.this.personGroupScrollPane.remove(HomeFrame.this.personGroupTable);
                //TODO

//                HomeFrame.this.personGroupScrollPane=new JScrollPane(HomeFrame.this.personGroupTable);
                JOptionPane.showMessageDialog(HomeFrame.this,"保存成功！");
//                HomeFrame.this.personGroupTable.validate();
//                HomeFrame.this.personGroupTable.updateUI();
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=HomeFrame.this.personGroupTable.getSelectedRow();
//                int row=HomeFrame.this.personGroupTable.rowAtPoint(e.getPoint());
                System.out.println("selected row:"+row);
                if(row<0){
                    JOptionPane.showMessageDialog(HomeFrame.this,"请选择一条数据!");
                    return;
                }
                Object id=HomeFrame.this.personGroupTable.getValueAt(row,0);
                if(id!=null&& StringUtils.isNotBlank(id.toString())){
                    HomeFrame.this.personGroupRepository.delete(id.toString());
                    HomeFrame.this.personGroups=personGroupRepository.findAll();
                }

                HomeFrame.this.personGroupTableModel.removeRow(row);
                HomeFrame.this.personGroupTableModel.setPersonGroups(HomeFrame.this.personGroups);
                JOptionPane.showMessageDialog(HomeFrame.this,"删除成功!");
//                HomeFrame.this.personGroupTable.validate();
//                HomeFrame.this.personGroupTable.updateUI();
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] newRow=new Object[4];
                HomeFrame.this.personGroups.add(new PersonGroup());
                HomeFrame.this.personGroupTableModel.addRow(newRow);
            }
        });
    }

    private void openFileChooser(ActionEvent e) {
        System.out.println("e.getSource():"+e.getSource());
        this.groupManagerPanel.setVisible(false);
        this.excelParentPanel.setVisible(true);
        this.excelParentPanel.add(this.excelFileChooser, BorderLayout.CENTER);

        int result = this.excelFileChooser.showOpenDialog(this.excelParentPanel);
        System.out.println(result);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = this.excelFileChooser.getSelectedFile();
            Excel excel = this.excelService.findByFilePath(file.getAbsolutePath());
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
            this.titleTextField.setText("当前文件: " +excel.getPath());
            redrawExcelDataPanel();
            this.titleTextField.revalidate();
            this.titleTextField.repaint();
            this.excelParentPanel.revalidate();
            this.excelParentPanel.repaint();
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
            }else{

            }
        });
        buttonGroupPanel.add(groupBtn);
        buttonGroupPanel.add(new JButton("刷新"));


        this.excelParentPanel.add(buttonGroupPanel,BorderLayout.SOUTH);
    }

}
