package com.lingyun.common.support.code;

public enum  UserAssessEnum {
    FEI_MANYI("0"),//非常满意
    MANYI("1"),//满意
    BU_MANYI("2"),//不满意
    BU_PINGJIA("3");//不予评价
    public final String code;

    UserAssessEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
