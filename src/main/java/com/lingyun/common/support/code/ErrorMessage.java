package com.lingyun.common.support.code;

public enum ErrorMessage {

    SUCCESS(true,"SUCCESS"),
    FAILURE(false,"failure");

    private boolean success;
    private String message;

    private ErrorMessage(boolean success,String message){
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
