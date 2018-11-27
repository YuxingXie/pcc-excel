package com.lingyun.common.support.code;

public enum StateEnum {

    NOT_STARTED(0),//未开始
    PENDING(1),  //进行中
    FINISHED(2); //已结束

    StateEnum(int code){
        this.code=code;
    }
    private final int code;

    public int getCode() {
        return code;
    }


}
