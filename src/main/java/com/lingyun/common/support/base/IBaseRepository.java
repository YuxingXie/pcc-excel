package com.lingyun.common.support.base;


import com.lingyun.common.support.data.JpqlAndParameterMap;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBaseRepository<T, ID extends Serializable> {
    List<T> findByCondition(List<DBSearchCondition> conditions);
    List<T> findByCondition(List<DBSearchCondition> conditions, int page, int size);

    Page<T> findPage(List<DBSearchCondition> conditions, int page, int size, long total, String jpql);
    Page<T> findPage(List<DBSearchCondition> conditions, int page, int size, String jpql);
    Page<T> findPage(List<DBSearchCondition> conditions, int page, int size);
    Page<T> findPage(int page, int size);

    List<T> findByJpql(String jpql, Map<String, Object> parameterMap);
    List<T> findByJpql(String jpql);

    Page<T> findPageByJpql(String jpql, Map<String, Object> parameterMap, int page, int size, int total);
    Page<T> findPageByJpql(String jpql, int page, int size, int total);

    long count(JpqlAndParameterMap jpqlAndParameterMap);
    long countAll();
    long findCountByCondition(List<DBSearchCondition> conditions);
    long findCountByCondition(List<DBSearchCondition> conditions, String countQueryString);
    List<T> findByJpqlAndParameterMap(JpqlAndParameterMap jpqlAndParameterMap);
    Page<T> findPageByJpqlAndParameterMap(JpqlAndParameterMap jpqlAndParameterMap, int page, int size);

}

