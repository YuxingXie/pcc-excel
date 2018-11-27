package com.lingyun.common.support.code;

public enum UploadFileType {

    IMAGES("images"),VIDEOS("videos"),DOCUMENTS("documents"),THUMBNAIL("thumbnail"),TEMP("temp");
    private String code;
    UploadFileType(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
