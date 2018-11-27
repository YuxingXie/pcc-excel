package com.lingyun.common.support.code;

public enum SexEnum {
    MALE(0),FEMALE(1);
    SexEnum(int code){
        this.code=code;
    }
    private final int code;

    public int getCode() {
        return code;
    }

}
