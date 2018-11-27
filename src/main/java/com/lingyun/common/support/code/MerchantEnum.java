package com.lingyun.common.support.code;

public enum MerchantEnum {

    PASS(1),//审核通过
    NOT_PASS(0),//审核未通过
    REFUSE(2);

    private Integer code;

    MerchantEnum(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
}
