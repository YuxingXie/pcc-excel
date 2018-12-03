package com.lingyun.projects.install.pccexcel.components.panels;

import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.common.support.util.file.XLSExcelUtils;
import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;
import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelExportReviewPanel extends TopComponent {
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
            ComponentsDrawTools.exportExcel(excelDataList,ExcelExportReviewPanel.this.excelService,ExcelExportReviewPanel.this);

        });
        add(southPanel,BorderLayout.SOUTH);
        add(this.excelDataTabbedPanel, BorderLayout.CENTER);

    }



}
