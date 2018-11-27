package com.lingyun.common.support.code;

public enum DefaultImageEnum {

    YES("Y"),//默认背景图片
    NO("N");//不是默认背景图片

    private String code;

    DefaultImageEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
