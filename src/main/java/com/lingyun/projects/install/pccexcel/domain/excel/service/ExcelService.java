package com.lingyun.projects.install.pccexcel.domain.excel.service;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExcelService {

    @Resource
    private ExcelRepository excelRepository;
    public Excel getCurrentExcel(){
        return null;
    }

    public Excel findByFilePath(String absolutePath) {
        List<Excel> excelList = excelRepository.findByPath(absolutePath);
        assert (BeanUtil.emptyCollection(excelList)||excelList.size()==1);

        return BeanUtil.emptyCollection(excelList)?null:excelList.get(0);
    }
    public Excel findByLastOpenDateGreatest(){
        return excelRepository.findByLastOpenDateGreatest();
    }
}
