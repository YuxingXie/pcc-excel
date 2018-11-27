package com.lingyun.common.support.code;

public enum JobEnum {
    //  0 表示 需要定时任务
    //  1 表示 不需要定时任务
    ING("0"),END("1");
    private String code;
    private JobEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
