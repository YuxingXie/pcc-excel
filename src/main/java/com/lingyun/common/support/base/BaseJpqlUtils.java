package com.lingyun.common.support.base;

import com.lingyun.common.support.util.string.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseJpqlUtils<T> {


    protected boolean isStringValue(Comparable value) {
        if (value instanceof String) return true;
        return false;
    }
    protected void handleString(String fieldName, String value, DBOperation operation, List<Predicate> criteria, CriteriaBuilder cb, Root<T> root) {
        if(operation!= DBOperation.LIKE&&operation!= DBOperation.EQ){
            throw new RuntimeException("string can not do any operations except 'like' and 'eq'!");
        }
        if (operation== DBOperation.LIKE)
            criteria.add(cb.like(root.get(fieldName),"'%"+value+"%'"));
        else
            criteria.add(cb.equal(root.get(fieldName),value));
    }
    protected void jpqlHandleString(String fieldName, String value, DBOperation operation, StringBuilder jpql, Map<String,Object> parameterMap) {
        if(operation!= DBOperation.LIKE&&operation!= DBOperation.EQ){
            throw new RuntimeException("string can not do any operations except 'like' and 'eq'!");
        }
        if (operation== DBOperation.LIKE){
            jpql.append(fieldName).append(" like ").append("concat('%',:"+getFieldParamVar(fieldName)+",'%')");
            parameterMap.put(getFieldParamVar(fieldName),value);
        }else{
            jpql.append(fieldName).append(" = :").append(getFieldParamVar(fieldName));
            parameterMap.put(getFieldParamVar(fieldName),value);
        }

    }
    @SuppressWarnings({"unchecked"})
    protected void handleComparable(String fieldName, Comparable value, DBOperation operation, List<Predicate> criteria, CriteriaBuilder cb, Root<T> root) {
        if(valueIsComparablePair(value)){
            if(operation!=(DBOperation.BETWEEN)){
                throw new RuntimeException("between operation must match a pair of values!");
            }
            ComparablePair comparablePair = (ComparablePair) value;
            Comparable first = comparablePair.getSmall();
            Comparable second = comparablePair.getBig();
            if (first!=null&&second!=null){
                criteria.add(cb.between(root.get(fieldName),first,second));
            }
            else if(second==null){
                criteria.add(cb.greaterThanOrEqualTo(root.get(fieldName),first));
            }else {criteria.add(
                    cb.lessThanOrEqualTo(root.get(fieldName),second));
            }
        }else {

        }



    }

    protected void hqlStringAndGetParameters(DBSearchCondition condition, StringBuilder jpql, Map<String,Object> parameterMap) {
        String fieldName=condition.getFieldName();
        DBOperation operation=condition.getOperation();
        Comparable value=condition.getValue();
        if (StringUtils.isBlank(fieldName)) return;
        if (value==null) return;
        if (operation==null) return;
        if (parameterMap==null) parameterMap=new HashMap<>();
        if(isNumberValue(value)){
            jpqlHandleNumber(fieldName,(Number)value,operation,jpql,parameterMap);
        }else if(isStringValue(value)){
            jpqlHandleString(fieldName,value.toString(),operation,jpql,parameterMap);
        }else{
            jpqlHandleComparable(fieldName,value,operation,jpql,parameterMap);
        }

    }
    protected void jpqlHandleComparable(String fieldName, Comparable value, DBOperation operation, StringBuilder jpql, Map<String,Object> parameterMap) {
        if(valueIsComparablePair(value)){
            if(operation!=(DBOperation.BETWEEN)){
                throw new RuntimeException("between operation must match a pair of values!");
            }
            ComparablePair comparablePair = (ComparablePair) value;
            Comparable first = comparablePair.getSmall();
            Comparable second = comparablePair.getBig();
            if (first!=null&&second!=null){
                jpql.append(fieldName).append(" between :"+getFieldParamVar(fieldName)+"Small and :"+getFieldParamVar(fieldName)+"Big");
                parameterMap.put(getFieldParamVar(fieldName)+"Small",first);
                parameterMap.put(getFieldParamVar(fieldName)+"Big",second);
            }
            else if(second==null){
                jpql.append(fieldName).append(" >= :"+getFieldParamVar(fieldName)+"Small");
                parameterMap.put(getFieldParamVar(fieldName)+"Small",first);

            }else {

                jpql.append(fieldName).append(" <= :"+getFieldParamVar(fieldName)+"Big");
                parameterMap.put(getFieldParamVar(fieldName)+"Big",second);
            }
        }else {

        }



    }
    protected boolean valueIsComparablePair(Comparable value) {
        if (value instanceof ComparablePair) return true;
        return false;
    }
    protected boolean isNumberValue(Comparable value) {
        if(value instanceof Number) return true;
        return false;
    }
    protected void handleNumber(String fieldName, Number value, DBOperation operation, List<Predicate> criteria, CriteriaBuilder cb, Root<T> root) {
        if (operation==(DBOperation.GE)){
            criteria.add(cb.ge(root.get(fieldName), value));
        }else if (operation==(DBOperation.EQ)){
            criteria.add(cb.equal(root.get(fieldName), value));
        }else if (operation==(DBOperation.GT)){
            criteria.add(cb.gt(root.get(fieldName), value));
        }else if (operation==(DBOperation.LE)){
            criteria.add(cb.le(root.get(fieldName),value));
        }else if (operation==(DBOperation.LT)){
            criteria.add(cb.lt(root.get(fieldName), value));
        }else if (operation==(DBOperation.BETWEEN)){
            throw new RuntimeException("single value can't convert to a between condition");
        }else {
            throw new RuntimeException("what is your operation?");
        }
    }
    protected void jpqlHandleNumber(String fieldName, Number value, DBOperation operation, StringBuilder jpql, Map<String,Object> parameterMap) {
        if (operation==(DBOperation.GE)){
            jpql.append(fieldName).append(" >= :").append(getFieldParamVar(fieldName));
            parameterMap.put(getFieldParamVar(fieldName),value);
        }else if (operation==(DBOperation.EQ)){
            jpql.append(fieldName).append(" = :").append(getFieldParamVar(fieldName));
            parameterMap.put(getFieldParamVar(fieldName),value);
        }else if (operation==(DBOperation.GT)){
            jpql.append(fieldName).append(" > :").append(getFieldParamVar(fieldName));
            parameterMap.put(getFieldParamVar(fieldName),value);
        }else if (operation==(DBOperation.LE)){
            jpql.append(fieldName).append(" <= :").append(getFieldParamVar(fieldName));
            parameterMap.put(getFieldParamVar(fieldName),value);
        }else if (operation==(DBOperation.LT)){
            jpql.append(fieldName).append(" < :").append(getFieldParamVar(fieldName));
            parameterMap.put(getFieldParamVar(fieldName),value);
        }else if (operation==(DBOperation.BETWEEN)){
            throw new RuntimeException("single value can't convert to a between condition");
        }else {
            throw new RuntimeException("what is your operation?");
        }
    }
    protected String getFieldParamVar(String fieldName){
        String dot="\\.";

        String[] fields=fieldName.split(dot);
        String ret=fields[0];
        for(int i=1;i<fields.length;i++){
            ret+= StringUtils.firstUpperCase(fields[i]);
        }
        return ret;
    }
}
