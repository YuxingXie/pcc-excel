package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.common.support.util.file.XLSExcelUtils;
import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;
import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class ExcelExportReviewPanel extends TopFramePanel{
    private JTabbedPane excelDataTabbedPanel;
    private ExcelDataRepository excelDataRepository;
    private JButton exportBtn;
    private ExcelService excelService;
    public ExcelExportReviewPanel(ExcelDataRepository excelDataRepository, ExcelService excelService) {
        this.excelService=excelService;
        this.excelDataRepository=excelDataRepository;
        loadData();

    }
    @Override
    public void loadData() {
        List<ExcelData> excelDataList=this.excelDataRepository.findByExcel(Constant.currentExcel);
//        List<ExcelData> excelDataList=this.excelDataRepository.findExcelDataListOrderByTotalCount(Constant.currentExcel);
        excelDataTabbedPanel= ComponentsDrawTools.drawTabbedPaneOfExcelExportReview(excelDataList);
        JPanel southPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        exportBtn=new JButton("导出");
        southPanel.add(exportBtn);
        exportBtn.addActionListener(e -> {
            String currentDir;
            if(Constant.currentExcel!=null){
                currentDir=Constant.currentExcel.getPath();
            }else {
                Excel excel=this.excelService.findByLastOpenDateGreatest();
                currentDir=excel==null?null:excel.getPath();
            }
            JFileChooser fileChooser=new JFileChooser(currentDir);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            this.add(fileChooser, BorderLayout.CENTER);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String fileName= DateTimeUtil.DateRepresentation.toString(new Date(),DateTimeUtil.DateFormatString.yyyyMMddHHmmss)+".xls";
                String exportFile=file.getAbsolutePath()+File.separator+fileName;
                ClassPathResource resource=new ClassPathResource("templates/template.xls");
                Map<String, List<ExcelData>> data= ComponentsDrawTools.excelDataListToMap(excelDataList);
                for (Map.Entry<String, List<ExcelData>>entry:data.entrySet()){

                }
                InputStream in = null;
                final String[] columnNames=new String[]{"排名","姓名","登录次数","浏览次数","点赞次数","评论次数","分享次数","总次数"};
                try {
                    in=resource.getInputStream();
                    XLSExcelUtils.exportExcelFromInputStream(in,exportFile,data,columnNames,0,0,true);
                    JOptionPane.showMessageDialog(ExcelExportReviewPanel.this,"文件“"+exportFile+"”导出成功!");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(ExcelExportReviewPanel.this,"程序异常!","警告:",JOptionPane.WARNING_MESSAGE);
                }finally {
                    if (null != in) {
                        try {
                            in.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }

        });
        add(southPanel,BorderLayout.SOUTH);
        add(this.excelDataTabbedPanel, BorderLayout.CENTER);

    }


}
