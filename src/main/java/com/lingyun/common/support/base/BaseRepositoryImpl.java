package com.lingyun.common.support.base;


import com.lingyun.common.support.data.Constant;
import com.lingyun.common.support.data.JpqlAndParameterMap;
import com.lingyun.common.support.util.string.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRepositoryImpl <T, ID extends Serializable> extends BaseJpqlUtils implements IBaseRepository<T, ID> {
    @PersistenceContext
    protected EntityManager em;
    private Class<T> entityClass;
    private Class<ID> idClass;
    private String tableAlias;

    public BaseRepositoryImpl() {
        Class typeCls = getClass();
        Type genType = typeCls.getGenericSuperclass();
        boolean genTypeInstanceOfParameterizedType = genType instanceof ParameterizedType;
        while (true) {
            if (!(genTypeInstanceOfParameterizedType)) {
                typeCls = typeCls.getSuperclass();
                genType = typeCls.getGenericSuperclass();
            } else {
                break;
            }
        }
        setActuallyClass((ParameterizedType) genType);
    }
    @SuppressWarnings({"unchecked","deprecation","serial"})
    private void setActuallyClass(ParameterizedType genType) {
        this.entityClass = (Class<T>) genType.getActualTypeArguments()[0];
        this.idClass = (Class<ID>) genType.getActualTypeArguments()[1];
        this.tableAlias= StringUtils.firstLowerCase(this.entityClass.getSimpleName());
    }


    public long findCountByCondition(List<DBSearchCondition> conditions){

        return findCountByCondition(conditions,null);
    }
    public List<T> findByCondition(List<DBSearchCondition> conditions){
        return findByCondition(conditions,0,0);
    }
    public List<T> findByCondition(List<DBSearchCondition> conditions,int page,int size){
        return findByCondition(conditions,page,size,null);
    }

    public List<T> findByCondition(List<DBSearchCondition> conditions,int page,int size,String jpql){

        if (size==0) size= Constant.DEFAULT_PAGE_SIZE;
        if (jpql==null) jpql= generateRootQueryString();
        jpql+=" where 1=1 ";
        Map<String,Object> paramMap=new HashMap<>();
        StringBuilder sb=new StringBuilder(jpql);
        handleConditions(conditions,paramMap,sb);
        JpqlAndParameterMap jpm=new JpqlAndParameterMap(sb.toString(),null,paramMap);

        TypedQuery<T> q=generateTypeQueryWithParams(jpm);
        q.setFirstResult(page*size+1).setMaxResults(size);
        return q.getResultList();
    }
    public long findCountByCondition(List<DBSearchCondition> conditions,String countJpql){
        StringBuilder countQuerySb=new StringBuilder();
        if (countJpql==null){
            countQuerySb= new StringBuilder(generateRootCountQueryString()).append(" where 1=1 ");
        }else{
            countQuerySb.append(countJpql);
        }
        Map<String,Object> paramMap=new HashMap<>();
        handleConditions(conditions,paramMap,countQuerySb);
        JpqlAndParameterMap jpm=new JpqlAndParameterMap(null,countQuerySb.toString(),paramMap);
        Query q=generateTypeQueryWithParams(jpm);

        return (Long) q.getSingleResult();
    }
    public Page<T> findPage(int page,int size){
        return findPage(null,page,size);
    }
    public Page<T> findPage(List<DBSearchCondition> conditions, int page, int size){

        return findPage(conditions,page,size,0,null);
    }

    public Page<T> findPage(List<DBSearchCondition> conditions, int page, int size,String jpql){
        long total=findCountByCondition(conditions);
        return findPage(conditions,page,size,total,jpql);
    }

    public Page<T> findPage(List<DBSearchCondition> conditions, int page, int size, long total, String jpql){
        List<T> list=findByCondition(conditions,page,size,jpql);
        Pageable pageable=new PageRequest(page,size);
        return new PageImpl<>(list,pageable,total);
    }
    protected void handleConditions(List<DBSearchCondition> conditions, Map<String,Object> parameterMap, StringBuilder jpql) {
        handleConditions(conditions, parameterMap, jpql,null);
    }
    @SuppressWarnings("unchecked")
    protected void handleConditions(List<DBSearchCondition> conditions, Map<String,Object> parameterMap, StringBuilder jpql,String tableAlias) {
        if (conditions==null) return;
        if (conditions.size()==0) return;
        if(tableAlias==null) tableAlias=this.tableAlias;
        if (parameterMap==null) parameterMap=new HashMap<>();
        for (DBSearchCondition condition:conditions){
            jpql.append(" and ").append(tableAlias).append(".");
            hqlStringAndGetParameters(condition,jpql,parameterMap);
            jpql.append(" ");
        }
    }
    public List<T> findByJpql(String jpql,Map<String,Object> parameterMap){
        JpqlAndParameterMap jpm=new JpqlAndParameterMap(jpql,null,parameterMap);
        TypedQuery<T >query= generateTypeQueryWithParams(jpm);
        return query.getResultList();
    }
    public Page<T> findPageByJpql(String jpql,Map<String,Object> parameterMap,int page,int size,int total){

        if (size==0) size= Constant.DEFAULT_PAGE_SIZE;
        JpqlAndParameterMap jnp=new JpqlAndParameterMap(jpql,null,parameterMap);
        TypedQuery<T >query= generateTypeQueryWithParams(jnp);
        query.setFirstResult(page*size+1);
        query.setMaxResults(size);
        List<T> list =query.getResultList();
        Pageable pageable=new PageRequest(page,size);
        return new PageImpl<>(list,pageable,total);
    }
    public List<T> findByJpql(String jpql){

        return findByJpql(jpql,null);
    }
    public Page<T> findPageByJpql(String jpql,int page,int size,int total){

        return findPageByJpql(jpql,null,page,size,total);
    }



    public long count(JpqlAndParameterMap jpqlAndParameterMap){
        if (jpqlAndParameterMap==null) return countAll();

        if(jpqlAndParameterMap.getCountQueryJpql()==null) return 0;

        Query query= generateCountQueryWithParams(jpqlAndParameterMap);
        return (Long) query.getSingleResult();
    }
    public long countAll(){
        Query query= generateCountQuery(null);
        return (Long) query.getSingleResult();
    }
    public List<T> findByJpqlAndParameterMap(JpqlAndParameterMap jpqlAndParameterMap){
        TypedQuery<T> query = generateTypeQueryWithParams(jpqlAndParameterMap);
        return query.getResultList();
    }
    public Page<T> findPageByJpqlAndParameterMap(JpqlAndParameterMap jpqlAndParamMap,int page,int size){
        if (jpqlAndParamMap==null) return findPage(page,size);

        if (size==0) size= Constant.DEFAULT_PAGE_SIZE;

        TypedQuery<T> query= generateTypeQueryWithParams(jpqlAndParamMap);
        query.setFirstResult(page*size+1);
        query.setMaxResults(size);
        List<T> list =query.getResultList();

        Query countQuery= generateCountQueryWithParams(jpqlAndParamMap);
        long total=(Long) countQuery.getSingleResult();
        Pageable pageable=new PageRequest(page,size);
        return new PageImpl<>(list,pageable,total);
    }
    protected TypedQuery<T> generateTypeQueryWithParams(JpqlAndParameterMap jpqlAndParameterMap) {

        if(jpqlAndParameterMap==null||jpqlAndParameterMap.getEntityQueryJpql()==null){
            return generateRootQuery();
        }
        Map<String ,Object> parameterMap=jpqlAndParameterMap.getParameterMap();
        String jpql=jpqlAndParameterMap.getEntityQueryJpql();
        TypedQuery<T> query=em.createQuery(jpql,entityClass);
        if (parameterMap!=null)
            for(String key:parameterMap.keySet()){
                query.setParameter(key,parameterMap.get(key));
            }
        return query;
    }
    protected Query generateCountQueryWithParams(JpqlAndParameterMap jpqlAndParameterMap) {

        if(jpqlAndParameterMap==null||jpqlAndParameterMap.getCountQueryJpql()==null){
            return generateCountQuery(generateRootCountQueryString());
        }
        Map<String ,Object> parameterMap=jpqlAndParameterMap.getParameterMap();
        String jpql=jpqlAndParameterMap.getCountQueryJpql();
        TypedQuery<Long> query=em.createQuery(jpql,Long.class);
        if (parameterMap!=null)
            for(String key:parameterMap.keySet()){
                query.setParameter(key,parameterMap.get(key));
            }
        return query;
    }
    protected TypedQuery<T> generateRootQuery() {
        return em.createQuery(generateRootQueryString(),entityClass);
    }

    protected String generateRootQueryString() {
        return "select " + tableAlias + " from " +entityClass.getSimpleName() + " " + tableAlias;
    }
    /**
     * 生成基础总数查询对象
     * @return 总数查询Query对象
     */
    protected Query generateCountQuery(String countQueryString) {
        if(countQueryString==null){
            countQueryString = generateRootCountQueryString();
        }
        return em.createQuery(countQueryString,Long.class);
    }

    /**
     * @apiNote 生成基础总数查询字符串
     * @return 字符串,类似于select count(user) from User user
     */
    protected String generateRootCountQueryString() {
        return "select count(" + tableAlias + ") from " +entityClass.getSimpleName() + " " + tableAlias;
    }
}

