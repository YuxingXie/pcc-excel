package com.lingyun.common.support.code;

public enum MessageLevel {
    INFO("info"),WARNING("warning"),WRONG("wrong");
    private String code;
    MessageLevel(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
