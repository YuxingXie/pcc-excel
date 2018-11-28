package com.lingyun.common.support.util.clazz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil extends BeanUtils {
    public static String javaToJson(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json=mapper.writeValueAsString(obj); //返回字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
    public static<T> T jsonToJava(String json,Class<T> toType){
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;//允许转义字符
//        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true) ;//允许转义字符
//        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true) ;
//        mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true) ;
        try {
            return mapper.readValue(json,toType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Map jsonToJMap(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Map.class);
    }


    public static boolean emptyCollection(Collection collection) {
        return collection==null||collection.size()==0;

    }
    public static boolean emptyMap(Map map) {
        return map==null||map.size()==0;
    }

    public static void main(String[] args){
        Map<String,String> map=new HashMap<>();
        System.out.println(emptyMap(map));
        map.put("a","b");
        System.out.println(emptyMap(map));
    }
}
