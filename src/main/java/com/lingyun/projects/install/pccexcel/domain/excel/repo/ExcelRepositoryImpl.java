package com.lingyun.projects.install.pccexcel.domain.excel.repo;

import com.lingyun.common.support.base.BaseRepositoryImpl;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;

import javax.persistence.Query;
import java.util.List;


public class ExcelRepositoryImpl extends BaseRepositoryImpl<Excel,String> {
    public Excel findByLastOpenDateGreatest(){
        StringBuffer jpql=new StringBuffer("SELECT excel FROM Excel excel ORDER BY excel.lastOpenDate desc");
        Query query=em.createQuery(jpql.toString()).setMaxResults(1);
        List<Excel> list=query.getResultList();
        return BeanUtil.emptyCollection(list)?null:list.get(0);
    }
}
