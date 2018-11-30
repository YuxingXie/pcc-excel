package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.common.support.util.file.OLE2OfficeExcelUtils;
import com.lingyun.common.support.util.string.StringUtils;
import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelPanel extends BasicPanel {
    private JPanel groupManagerPanel;
    private JFileChooser excelFileChooser;
    private JTextField titleTextField;
    private JTabbedPane excelDataPanel;
    private ExcelService excelService;
    private JTable personGroupTable;
    private PersonGroupTableModel personGroupTableModel;
    private JScrollPane personGroupScrollPane;
    private List<PersonGroup> personGroups;

    private ExcelRepository excelRepository;
    private PersonGroupRepository personGroupRepository;
    private ExcelDataRepository excelDataRepository;
    public ExcelPanel(ExcelService excelService,PersonGroupRepository personGroupRepository,ExcelDataRepository excelDataRepository) {
        this.excelService=excelService;
        this.personGroupRepository = personGroupRepository;
        this.excelDataRepository = excelDataRepository;
        initComponents();
        addBottomButtonGroup();
        
    }

    protected void initComponents() {
        this.groupManagerPanel =new JPanel();
        this.groupManagerPanel.setBackground(Color.LIGHT_GRAY);
        String currentDir;
        if(Constant.currentExcel!=null){
            currentDir=Constant.currentExcel.getPath();
        }else {
            Excel excel=excelService.findByLastOpenDateGreatest();
            currentDir=excel==null?null:excel.getPath();
        }
        this.excelFileChooser=new JFileChooser(currentDir);
        FileFilter filter1 =new FileNameExtensionFilter("microsoft excel files","xls","xlsx","xlt","xml","xlsm","xlsb","xltx","xla","xlw","xlr");
        this.excelFileChooser.setFileFilter(filter1);
        this.titleTextField =new JTextField();
        this.titleTextField.setText("what's new?");
        this.titleTextField.setEditable(false);

        this.excelDataPanel=excelDataPanel();
        this.titleTextField.setText("当前文件: "+(Constant.currentExcel==null?"没有选择excel文件":Constant.currentExcel.getPath()));
        this.add(this.excelDataPanel);
        JLabel label=new JLabel("双击单元格修改数据且不会覆盖原始文件，点击\"保存\"后会导出到excel中；点击\"刷新\"可以重新载入excel内容。");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label,BorderLayout.NORTH);
        this.personGroups =personGroupRepository.findAll();
        this.personGroupTableModel=new PersonGroupTableModel(ComponentsDrawTools.getRowDataOfPersonGroups(this.personGroups),ComponentsDrawTools.getColumnNamesOfPersonGroups());
        this.personGroupTable=new JTable(this.personGroupTableModel);
        this.personGroupTable.setGridColor(new Color(230, 230, 230));
        this.personGroupTable.getTableHeader().setPreferredSize(new Dimension(50, 30));
        this.personGroupScrollPane=new JScrollPane(this.personGroupTable);
    }
    private void redrawExcelDataPanel() {
        ExcelPanel.this.excelDataPanel=ComponentsDrawTools.drawTabbedPaneByExcel(Constant.currentExcel,ExcelPanel.this.excelDataPanel);
        ExcelPanel.this.excelDataPanel.revalidate();
        ExcelPanel.this.excelDataPanel.repaint();
    }



    //TODO:Many problems
    public void renderGroupManagerPanel() {
        System.out.println("渲染用户分组组件");
        this.setVisible(false);
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
                for(int i=0;i<ExcelPanel.this.personGroupTable.getRowCount();i++){
                    PersonGroup personGroup=new PersonGroup();
                    Object _id=ExcelPanel.this.personGroupTable.getValueAt(i,0);
                    if(_id!=null&&!_id.toString().trim().equals("")){
                        personGroup.setId(ExcelPanel.this.personGroupTable.getValueAt(i,0).toString());
                    }
                    Object groupName=ExcelPanel.this.personGroupTable.getValueAt(i,1);
                    if(groupName==null||groupName.toString().trim().equals("")){
                        JOptionPane.showMessageDialog(ExcelPanel.this,"第 "+(i+1)+" 行 分组名称不能为空");
                        return;
                    }
                    personGroup.setGroupName(groupName.toString());

                    Object description=ExcelPanel.this.personGroupTable.getValueAt(i,2);
                    if(description!=null&&!description.toString().trim().equals("")){
                        personGroup.setDescription(ExcelPanel.this.personGroupTable.getValueAt(i,2).toString());
                    }
                    Object createDate=ExcelPanel.this.personGroupTable.getValueAt(i,3);
                    if(createDate!=null&&!createDate.toString().trim().equals("")){
                        personGroup.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,ExcelPanel.this.personGroupTable.getValueAt(i,3).toString()));
                    }
                    personGroupsToAdd.add(personGroup);
                }

                ExcelPanel.this.personGroups=ExcelPanel.this.personGroupRepository.save(personGroupsToAdd);
                System.out.println(BeanUtil.javaToJson(ExcelPanel.this.personGroups));
                ExcelPanel.this.personGroupTableModel.setPersonGroups(ExcelPanel.this.personGroups);
//                ExcelPanel.this.personGroupScrollPane.remove(ExcelPanel.this.personGroupTable);
                //TODO

//                ExcelPanel.this.personGroupScrollPane=new JScrollPane(ExcelPanel.this.personGroupTable);
                JOptionPane.showMessageDialog(ExcelPanel.this,"保存成功！");
//                ExcelPanel.this.personGroupTable.validate();
//                ExcelPanel.this.personGroupTable.updateUI();
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=ExcelPanel.this.personGroupTable.getSelectedRow();
//                int row=ExcelPanel.this.personGroupTable.rowAtPoint(e.getPoint());
                System.out.println("selected row:"+row);
                if(row<0){
                    JOptionPane.showMessageDialog(ExcelPanel.this,"请选择一条数据!");
                    return;
                }
                Object id=ExcelPanel.this.personGroupTable.getValueAt(row,0);
                if(id!=null&& StringUtils.isNotBlank(id.toString())){
                    ExcelPanel.this.personGroupRepository.delete(id.toString());
                    ExcelPanel.this.personGroups=personGroupRepository.findAll();
                }

                ExcelPanel.this.personGroupTableModel.removeRow(row);
                ExcelPanel.this.personGroupTableModel.setPersonGroups(ExcelPanel.this.personGroups);
                JOptionPane.showMessageDialog(ExcelPanel.this,"删除成功!");
//                ExcelPanel.this.personGroupTable.validate();
//                ExcelPanel.this.personGroupTable.updateUI();
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] newRow=new Object[4];
                ExcelPanel.this.personGroups.add(new PersonGroup());
                ExcelPanel.this.personGroupTableModel.addRow(newRow);
            }
        });
    }

    public void openFileChooser(ActionEvent e) {
        System.out.println("e.getSource():"+e.getSource());
        this.groupManagerPanel.setVisible(false);
        this.setVisible(true);
        this.add(this.excelFileChooser, BorderLayout.CENTER);

        int result = this.excelFileChooser.showOpenDialog(this);
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
            excel=excelService.save(excel);
            Constant.currentExcel=excel;
            this.titleTextField.setText("当前文件: " +excel.getPath());
            redrawExcelDataPanel();
            this.titleTextField.revalidate();
            this.titleTextField.repaint();
            this.revalidate();
            this.repaint();
        }
    }

    private void addBottomButtonGroup() {
        JPanel buttonGroupPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton=new JButton("检查");
        saveButton.addActionListener(e -> {
            JOptionPane.showConfirmDialog(ExcelPanel.this.excelDataPanel,"当前没有excel文件，请先导入文件。","提示",JOptionPane.OK_OPTION);

        });
        buttonGroupPanel.add(saveButton);
        JButton groupBtn=new JButton("分组预览");
        groupBtn.addActionListener(e -> {
            if(Constant.currentExcel==null){
                int selection=JOptionPane.showConfirmDialog(ExcelPanel.this.excelDataPanel,"当前没有excel文件，请先导入文件。","提示",JOptionPane.OK_OPTION);
                if(selection==JOptionPane.OK_OPTION){
                    openFileChooser(e);
                }
            }else{
                ExcelPanel.this.setVisible(false);
                JPanel excelGroupReviewPanel=new ExcelExportReviewPanel(ExcelPanel.this.excelDataRepository);
                ExcelPanel.this.add(excelGroupReviewPanel);
            }
        });
        buttonGroupPanel.add(groupBtn);
        buttonGroupPanel.add(new JButton("刷新"));


        this.add(buttonGroupPanel,BorderLayout.SOUTH);
    }
    private JTabbedPane excelDataPanel(){
        Excel excel = this.excelService.findByLastOpenDateGreatest();
        JTabbedPane jTabbedpane = new JTabbedPane();
        if(excel==null) {
            JPanel tabPanel=new JPanel(new FlowLayout());
            JLabel label = new JLabel("当前没有excel文件");
            label.setForeground(Color.RED);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalTextPosition(SwingConstants.CENTER);

            tabPanel.add(label);
            JButton importExcelBtn=new JButton("导入excel...");
            importExcelBtn.addActionListener(e -> {
                openFileChooser(e);
            });
            tabPanel.add(importExcelBtn);
            jTabbedpane.addTab("没有选择文件",tabPanel);
            return jTabbedpane;
        }
        return ComponentsDrawTools.drawTabbedPaneByExcel(excel, jTabbedpane);
    }
}
