package com.lingyun.common.support.data;

import java.util.Map;

public class JpqlAndParameterMap {
    private String entityQueryJpql;
    private String countQueryJpql;
    private Map<String,Object> parameterMap;

    public JpqlAndParameterMap(String entityQueryJpql, String countQueryJpql, Map<String, Object> parameterMap) {
        this.entityQueryJpql = entityQueryJpql;
        this.countQueryJpql = countQueryJpql;
        this.parameterMap = parameterMap;
    }

    public String getEntityQueryJpql() {
        return entityQueryJpql;
    }

    public void setEntityQueryJpql(String entityQueryJpql) {
        this.entityQueryJpql = entityQueryJpql;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getCountQueryJpql() {
        return countQueryJpql;
    }

    public void setCountQueryJpql(String countQueryJpql) {
        this.countQueryJpql = countQueryJpql;
    }
}
