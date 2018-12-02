package com.lingyun.projects.install.pccexcel.domain.excel.repo;

import com.lingyun.common.support.base.BaseRepositoryImpl;
import com.lingyun.common.support.data.JpqlAndParameterMap;
import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelDataTotalCount;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ExcelDataRepositoryImpl extends BaseRepositoryImpl<ExcelData,String> {
    public int deleteAllByExcel(Excel excel){
        StringBuffer jpql=new StringBuffer("delete from ExcelData e where e.excel.id=?1");

        Query query=em.createQuery(jpql.toString()).setParameter(1,excel.getId());

        return query.executeUpdate();

    }


    public List<ExcelDataTotalCount> findExcelDataListOrderByTotalCount(Excel excel){

        StringBuilder jpql=new StringBuilder("select new com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelDataTotalCount(" );
        jpql.append("(da.loginCount+da.viewCount+da.praiseCount+da.commentCount+da.shareCount) as totalCount,");
        jpql.append("da.loginCount as loginCount,");
        jpql.append("da.viewCount as viewCount,");
        jpql.append("da.praiseCount as praiseCount,");
        jpql.append("da.commentCount as commentCount,");
        jpql.append("da.shareCount as shareCount,");
        jpql.append("da.description as description,");
        jpql.append("da.person as person");
        jpql.append(")from ExcelData da where da.excel.id=?1");
        jpql.append(" order by totalCount");
        TypedQuery<ExcelDataTotalCount> query=em.createQuery(jpql.toString(),ExcelDataTotalCount.class);
        query.setParameter(1,excel.getId());
        return query.getResultList();
    }

}
