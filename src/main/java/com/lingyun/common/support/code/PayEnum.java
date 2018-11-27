package com.lingyun.common.support.code;

public enum PayEnum {

    YES("Y"), //活动需要付费
    NO("N"); //活动不需要付费

    private String code;

    PayEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
