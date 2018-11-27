package com.lingyun.common.annotation;

import com.google.gson.Gson;
import com.lingyun.common.support.util.clazz.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SmsContent {
    private static final Logger logger = LoggerFactory.getLogger(SmsContent.class);
    private Map<String,String> contents;
    public SmsContent(){}
    public SmsContent(String key,String value){
        addContent(key,value);
    }
    public void addContent(String key,String value){
        if (contents==null) contents=new HashMap<>();
        contents.put(key,value);
    }
    public String getContentsJsonString(){
        if (contents==null) return null;
        if (contents.keySet()==null) return null;
        if (BeanUtil.emptyCollection(contents.keySet())) return null;
        return new Gson().toJson(contents);
    }
    public static void main(String[] args){
        SmsContent content=new SmsContent();
        content.addContent("code","8989898");
        logger.info(content.getContentsJsonString());
    }
}
