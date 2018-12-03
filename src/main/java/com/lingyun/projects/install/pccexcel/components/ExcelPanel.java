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
import com.lingyun.projects.install.pccexcel.route.JPanelRouter;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

public class ExcelPanel extends TopFramePanel{

    private JFileChooser excelFileChooser;
    private JTabbedPane excelDataPanel;
    private ExcelService excelService;

    private ExcelRepository excelRepository;
    private ExcelDataRepository excelDataRepository;
    private JPanelRouter observer;
    public ExcelPanel(ExcelService excelService, ExcelDataRepository excelDataRepository, JPanelRouter observer) {
        super();
        this.observer=observer;
        this.excelService=excelService;
        this.excelDataRepository = excelDataRepository;
        loadData();

    }

    @Override
    public void loadData() {
        String currentDir;
        if(Constant.currentExcel!=null){
            currentDir=Constant.currentExcel.getPath();
        }else {
            Excel excel=this.excelService.findByLastOpenDateGreatest();
            currentDir=excel==null?null:excel.getPath();
        }
        this.excelFileChooser=new JFileChooser(currentDir);
        FileFilter filter1 =new FileNameExtensionFilter("microsoft excel files","xls","xlsx","xlt","xml","xlsm","xlsb","xltx","xla","xlw","xlr");
        this.excelFileChooser.setFileFilter(filter1);



        loadJTabbedPane();
        this.add(this.excelDataPanel);
        JLabel label=new JLabel("双击单元格修改数据且不会覆盖原始文件，点击\"保存\"后会导出到excel中；点击\"刷新\"可以重新载入excel内容。");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label,BorderLayout.NORTH);
        addBottomButtonGroup();

    }
    private void redrawExcelDataPanel() {
        if(this.excelDataPanel!=null){
            this.excelDataPanel.removeAll();
        }else {
            this.excelDataPanel = new JTabbedPane();
        }
        SortedMap<String, List<List<Object>>> result = Constant.currentExcel.toSortedMap();
        for(String sheetName:result.keySet()){
            JPanel tabPanel=new JPanel(new BorderLayout());
            List<List<Object>> sheetData = result.get(sheetName);
            final Object[][] rowData=new Object[sheetData.size()][sheetData.get(0).size()];
            final Object[] columnNames=sheetData.get(0).toArray();
            for(int i=1;i<sheetData.size();i++){
                List<Object> _rowData= sheetData.get(i);
                for (int j=0;j<_rowData.size();j++){
                    rowData[i-1][j] =_rowData.get(j);
                }
            }
            JTable table = new JTable(rowData,columnNames){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JScrollPane jScrollPane=new JScrollPane(table);
            tabPanel.add(jScrollPane,BorderLayout.CENTER);

            this.excelDataPanel.addTab(sheetName,tabPanel);
            this.excelDataPanel.validate();
            this.excelDataPanel.repaint();
        }
    }

    private void addBottomButtonGroup() {
        JPanel buttonGroupPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton importButton=new JButton("导入excel");
        importButton.addActionListener(e -> {
            openFileChooser(e);
        });
        buttonGroupPanel.add(importButton);
        JButton preview=new JButton("导出预览");
        preview.addActionListener(e -> {
            if(Constant.currentExcel==null){
                int selection=JOptionPane.showConfirmDialog(ExcelPanel.this.excelDataPanel,"当前没有excel文件，请先导入文件。","提示",JOptionPane.OK_OPTION);
                if(selection==JOptionPane.OK_OPTION){
                    openFileChooser(e);
                }
            }else{
//                ExcelPanel.this.setVisible(false);
//                JPanel excelGroupReviewPanel=new ExcelExportReviewPanel(ExcelPanel.this.excelDataRepository);
//                ExcelPanel.this.add(excelGroupReviewPanel);
                ExcelPanel.this.observer.navigateTo("excelExportReviewPanel");
            }
        });
        buttonGroupPanel.add(preview);
        buttonGroupPanel.add(new JButton("刷新"));


        this.add(buttonGroupPanel,BorderLayout.SOUTH);
    }

    public void loadJTabbedPane() {
        Excel excel = this.excelService.findByLastOpenDateGreatest();
        Constant.currentExcel=excel;
        this.excelDataPanel = new JTabbedPane();
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
            this.excelDataPanel.addTab("没有选择文件",tabPanel);


        }else {
            redrawExcelDataPanel();
        }

    }
    public void openFileChooser(ActionEvent e) {
        System.out.println("e.getSource():"+e.getSource());
        this.add(this.excelFileChooser, BorderLayout.CENTER);
        int result = this.excelFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.setVisible(true);
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
//            System.out.println(new String(excel.getDataJson(),StandardCharsets.UTF_8));
            excel=excelService.save(excel);
            Constant.currentExcel=excel;
            redrawExcelDataPanel();
//            this.revalidate();
//            this.repaint();
        }
    }


}
