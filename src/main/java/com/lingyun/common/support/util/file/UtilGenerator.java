package com.lingyun.common.support.util.file;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class UtilGenerator {
    private static final Logger logger = LoggerFactory.getLogger(UtilGenerator.class);
    //获取32为UUID字符串
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
    //获取字符串的后缀名
    public static String getSuffix(String filename){
        return filename.substring(filename.lastIndexOf(".")+1);
    }
    //判断后缀名或否在集合中
    public static boolean checkSuffix(String suffix, String[] list){
        return Arrays.asList(list).contains(suffix);
    }

    public static void thumbnails(String path,int width,int height,String toPic){
        try {
            Thumbnails.of(path).size(width,height).toFile(toPic);
            logger.error("图片压缩成功"+path);

        } catch (IOException e) {
            logger.error("图片压缩异常:"+e.getMessage());

        }
    }


}
