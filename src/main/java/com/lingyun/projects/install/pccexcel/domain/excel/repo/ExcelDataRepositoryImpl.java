package com.lingyun.projects.install.pccexcel.domain.excel.repo;

import com.lingyun.common.support.base.BaseRepositoryImpl;
import com.lingyun.common.support.data.JpqlAndParameterMap;
import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;

import javax.persistence.Query;
import java.util.List;


public class ExcelDataRepositoryImpl extends BaseRepositoryImpl<ExcelData,String> {
    public int deleteAllByExcel(Excel excel){
        StringBuffer jpql=new StringBuffer("delete from ExcelData e where e.excel.id=?1");

        Query query=em.createQuery(jpql.toString()).setParameter(1,excel.getId());

        return query.executeUpdate();

    }
}
