package com.lingyun.projects.install.pccexcel.domain.excel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;

import java.util.List;

@Repository
public interface ExcelRepository extends JpaRepository<Excel, String> {

    List<Excel> findByPath(String absolutePath);

    Excel findByLastOpenDateGreatest();
}
