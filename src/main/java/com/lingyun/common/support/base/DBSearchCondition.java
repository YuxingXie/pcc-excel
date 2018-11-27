package com.lingyun.common.support.base;

public class DBSearchCondition {
    private String fieldName;
    private DBOperation operation;
    private Comparable value;
    public DBSearchCondition(){}
    public DBSearchCondition(String fieldName, DBOperation operation, Comparable value) {
        this.fieldName = fieldName;
        this.operation = operation;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public DBOperation getOperation() {
        return operation;
    }

    public void setOperation(DBOperation operation) {
        this.operation = operation;
    }

    public Comparable getValue() {
        return value;
    }

    public void setValue(Comparable value) {
        this.value = value;
    }

}
