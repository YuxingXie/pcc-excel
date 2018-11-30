package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import java.util.List;

public class ExcelExportReviewPanel extends JPanel{
    private JTabbedPane excelDataTabbedPanel;
    ExcelDataRepository excelDataRepository;
    public ExcelExportReviewPanel(ExcelDataRepository excelDataRepository) {
        this.excelDataRepository=excelDataRepository;
        List<ExcelData> excelDataList=this.excelDataRepository.findByExcel(Constant.currentExcel);
        excelDataTabbedPanel=ComponentsDrawTools.drawTabbedPaneOfExcelExportReview(excelDataList);
        add(this.excelDataTabbedPanel);

    }
}
