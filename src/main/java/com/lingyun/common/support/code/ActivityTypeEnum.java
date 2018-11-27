package com.lingyun.common.support.code;

public enum  ActivityTypeEnum {
    COMMON("support"),CUSTOM("custom");
    private String code;
    ActivityTypeEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
