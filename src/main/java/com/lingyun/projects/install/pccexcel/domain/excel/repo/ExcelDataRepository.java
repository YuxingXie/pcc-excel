package com.lingyun.projects.install.pccexcel.domain.excel.repo;

import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcelDataRepository extends JpaRepository<ExcelData, String> {

    List<ExcelData> findByExcel(Excel excelId);
    int deleteAllByExcel(Excel excel);
}
