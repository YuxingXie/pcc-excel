package com.lingyun.common.support.code;

public enum FlagEnum {

    YES("Y"),//背景图片
    NO("N");//消息图片

    private String code;

    FlagEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
