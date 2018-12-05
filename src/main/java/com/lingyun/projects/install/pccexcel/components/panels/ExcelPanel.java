package com.lingyun.projects.install.pccexcel.components.panels;

import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.route.JPanelRouter;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.SortedMap;

public class ExcelPanel extends TopComponent {


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
        loadJTabbedPane();
        this.add(this.excelDataPanel);
        JLabel label=new JLabel("双击单元格修改数据且不会覆盖原始文件，点击\"保存\"后会导出到excel中；点击\"刷新\"可以重新载入excel内容。");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label,BorderLayout.NORTH);
        addBottomButtonGroup();

    }
    //TODO 感觉有问题
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
        openFileChooser(importButton);
        buttonGroupPanel.add(importButton);
        JButton preview=new JButton("导出预览");
        preview.addActionListener(e -> {
            if(Constant.currentExcel==null){
                int selection=JOptionPane.showConfirmDialog(ExcelPanel.this.excelDataPanel,"当前没有excel文件，请先导入文件。","提示",JOptionPane.OK_OPTION);
                if(selection==JOptionPane.OK_OPTION){
                    int result=ComponentsDrawTools.openFileChooser(ExcelPanel.this.excelService,ExcelPanel.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        reload();
                    }
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

    private void openFileChooser(JButton importButton) {
        importButton.addActionListener(e -> {
            int result=ComponentsDrawTools.openFileChooser(ExcelPanel.this.excelService,ExcelPanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                reload();
            }
        });
    }

    public void loadJTabbedPane() {
        Excel excel = Constant.currentExcel;
        if(excel==null){
            excel=this.excelService.findByLastOpenDateGreatest();
            Constant.currentExcel=excel;
        }


        this.excelDataPanel = new JTabbedPane();
        if(excel==null) {
            JPanel tabPanel=new JPanel(new FlowLayout());
            JLabel label = new JLabel("当前没有excel文件");
            label.setForeground(Color.RED);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalTextPosition(SwingConstants.CENTER);

            tabPanel.add(label);
            JButton importExcelBtn=new JButton("导入excel...");
            openFileChooser(importExcelBtn);
            tabPanel.add(importExcelBtn);
            this.excelDataPanel.addTab("没有选择文件",tabPanel);


        }else {
            redrawExcelDataPanel();
        }

    }



}
