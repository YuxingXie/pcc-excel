package com.lingyun.common.support.util.string;


//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class MD5 {
    private static char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
//    private static Logger logger = LogManager.getLogger();

    private MD5() {
    }

    public static String convert(String s) {
        return convert(s, hexChars);
    }

    public static String convert(String s, char[] hex) {
        try {
            byte[] bytes = s.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hex[b >>> 4 & 0xf];
                chars[k++] = hex[b & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
//            logger.error("MD5 Generate error", e);
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        String o="123123test";
        byte[] b= o.getBytes(StandardCharsets.UTF_8);
        Base64 base64=new Base64();
//        String aa=new BASE64Encoder().encode(b);
        String bb=new String(base64.encode(b));
//        logger.info("aa:"+aa);
//        logger.info("aa.length:"+aa.length());
//        logger.info("bb:"+bb);
//        logger.info("bb.length:"+bb.length());
//        logger.info(aa.equalsIgnoreCase(bb.trim()));




//        BASE64Decoder decoder = new BASE64Decoder();
//        byte[] b2 = decoder.decodeBuffer(o);
//        byte[] b1=Base64.decodeBase64(o);
////        byte[] b1=base64.decode(o);
//
//
//        String sb1= new String(b1).trim();
//        String sb2= new String(b2).trim();
//        logger.info(sb1);
//        logger.info(sb2);
//        logger.info(sb1.equalsIgnoreCase(sb2));

    }



    }
