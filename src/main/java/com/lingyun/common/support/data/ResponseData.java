package com.lingyun.common.support.data;

import com.lingyun.common.support.code.MessageLevel;

import java.util.Date;

public class ResponseData<T> {

    private boolean success;
    private String message;
    private Date sendDate;//发送时间，可以用来处理服务器与客户端时间不统一的问题
    /**
     * level可返回警告，消息，错误几种级别，方便前端根据此字段使用样式，默认值是info
     */
    private String level ;

    private T data;

    public ResponseData() {
        init();
    }

    public ResponseData(boolean success) {
        this.success = success;
        init();
    }
    public ResponseData(boolean success, String message) {
        this.success = success;
        this.message = message;
        init();
    }
    public ResponseData(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        init();
    }

    public ResponseData(boolean success, String message, String level, T data) {
        this.success = success;
        this.message = message;
        this.level = level;
        this.data = data;
        init();
    }
    private void init(){
        if(this.level!=null) return;
        if(this.success){
            this.level=MessageLevel.INFO.getCode();
        }
        else {
            this.level=MessageLevel.WRONG.getCode();
        }
        this.sendDate = new Date();
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

    public T getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
