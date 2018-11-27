package com.lingyun.projects.install.pccexcel.domain.service;

import com.lingyun.common.support.util.file.ExcelUtils;
import com.lingyun.projects.install.pccexcel.domain.entity.Excel;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {

    public Excel getCurrentExcel(){
        return null;
    }

    public String[] getColumnNames(String id){
        return ExcelUtils.getColumnNames(id);
    }

    public Object[][] getRowData(String id){
        return ExcelUtils.getRowData(id);
    }
}
