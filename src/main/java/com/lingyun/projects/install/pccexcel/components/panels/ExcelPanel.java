package com.lingyun.projects.install.pccexcel.components.panels;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.file.OLE2OfficeExcelUtils;
import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.route.JPanelRouter;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

public class ExcelPanel extends TopComponent {

    private ExcelService excelService;
    private JPanelRouter observer;
    private LeftMenuTreeComponent menuTree;
    public ExcelPanel(LeftMenuTreeComponent menuTree,ExcelService excelService,  JPanelRouter observer) {
        super();
        this.menuTree=menuTree;
        this.observer=observer;
        this.excelService=excelService;
        loadData();
    }

    @Override
    public void loadData() {
        JTabbedPane excelDataPanel = new JTabbedPane();
        this.add(excelDataPanel);
        Excel excel = Constant.currentExcel;
        if(excel==null){
            excel=this.excelService.findByLastOpenDateGreatest();
            Constant.currentExcel=excel;
        }

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
            excelDataPanel.addTab("没有选择文件",tabPanel);
        }else {
            SortedMap<String, List<List<Object>>> result = excel.toSortedMap();
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
                excelDataPanel.addTab(sheetName,tabPanel);

            }
        }



        JLabel label=new JLabel("双击单元格修改数据且不会覆盖原始文件，点击\"保存\"后会导出到excel中；点击\"刷新\"可以重新载入excel内容。");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label,BorderLayout.NORTH);
        addBottomButtonGroup(excelDataPanel);

    }


    private void addBottomButtonGroup(JTabbedPane excelDataPanel) {
        JPanel buttonGroupPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton importButton=new JButton("导入excel");
        openFileChooser(importButton);
        buttonGroupPanel.add(importButton);
        JButton preview=new JButton("导出预览");
        preview.addActionListener(e -> {
            if(Constant.currentExcel==null){
                int selection=JOptionPane.showConfirmDialog(excelDataPanel,"当前没有excel文件，请先导入文件。","提示",JOptionPane.OK_OPTION);
                if(selection==JOptionPane.OK_OPTION){
                    int result=ComponentsDrawTools.openFileChooser(ExcelPanel.this.excelService,ExcelPanel.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        reload();
                    }
                }
            }else{
                ExcelPanel.this.observer.navigateTo("excelExportReviewPanel");
            }
        });
        buttonGroupPanel.add(preview);
        JButton refreshBtn=new JButton("刷新");
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Excel excel =Constant.currentExcel;
                String json= null;
                try {
                    File file=new File(excel.getPath());
                    if (!file.exists()){
                        JOptionPane.showMessageDialog(ExcelPanel.this,"文件已经从原位置移除，无法刷新!");
                        return;
                    }
                    json = BeanUtil.javaToJson(OLE2OfficeExcelUtils.getData(file));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                excel.setLastOpenDate(new Date());
                excel.setDataJson(json==null?null:json.getBytes(StandardCharsets.UTF_8));
                excel=excelService.save(excel);
                Constant.currentExcel=excel;
            }
        });
        buttonGroupPanel.add(refreshBtn);
        this.add(buttonGroupPanel,BorderLayout.SOUTH);
    }

    private void openFileChooser(JButton importButton) {
        importButton.addActionListener(e -> {
            int result=ComponentsDrawTools.openFileChooser(ExcelPanel.this.excelService,ExcelPanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                reload();
                this.menuTree.loadData();
            }
        });
    }

}
