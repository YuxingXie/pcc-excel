package com.lingyun.common.configs;


import com.lingyun.common.annotation.SmsAdapter;
import com.lingyun.common.support.util.web.HttpRequestor;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//import com.lingyun.support.util.string.MD5;


/**
 * Created by Administrator on 2016/1/9.
 */
@Component
public class SmsManager implements SmsAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SmsManager.class);

    public SmsManager(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }

    private SmsConfig smsConfig;
    public String send(Map<String,String> codes){
       return null;
    }

    private static final String ECNAME = "宁乡县人民政府办公室";
    private static final String CONTENT = "您注册的智慧宁乡12345平台验证码为";

    //拼接短信接口需要的数据  进行post提交
    public String sendva(String mobile,String validCode){
        Map<String ,String> stringMap=new HashMap<>();
            stringMap.put("ecName",ECNAME);
            stringMap.put("apId",smsConfig.getUserId());
            stringMap.put("secretKey",smsConfig.getPwd());
            stringMap.put("mobiles",mobile);
            stringMap.put("content",validCode);
            stringMap.put("sign",smsConfig.getSign());
            stringMap.put("addSerial","");
            String content = ECNAME+smsConfig.getUserId()
                    +smsConfig.getPwd()+mobile+validCode+smsConfig.getSign();
            SimpleHash sh = new SimpleHash("MD5",content,null,1);
            stringMap.put("mac",sh.toString());
        try {
            String str = new HttpRequestor().doPost(smsConfig.getAddr(), stringMap,1);
            logger.info("-------短信返回值-----"+str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    @Override
    public String sendValidCode(String mobile,String validCode) {
        try {
            Map<String ,String[]> stringMap=new HashMap<>();
            String result = sendva(mobile, validCode);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    private Map<String,String> putParams(String mobile,String validCode) {
//        Map<String,String> params=new HashMap<>();
//        params.put("uid",smsConfig.getUserId());
////        params.put("pwd",MD5.convert(smsConfig.getPwd()+smsConfig.getUserId()));
////        params.put("encode",smsConfig.getEncode());
//        params.put("mobile",mobile);
//       // params.put("template",smsConfig.getSignUpValidCodeTemplate());
//        params.put("content", new SmsContent("code",validCode).getContentsJsonString());
//        return params;
//    }


    /**
     * 将返回状态编码转化为描述结果
     *
     *            打印信息
     * @param result
     *            状态编码
     * @return 描述结果
     */
    private static String getResponse(String result) {
        if (result.equals("100")) {
            return "发送成功";
        }
        else if (result.equals("101")) {
            return "验证失败";
        }
        else if (result.equals("102")) {
            return "短信不足";
        }
        else if (result.equals("103")) {
            return "操作失败";
        }
        else if (result.equals("104")) {
            return "非法字符";
        }
        else if (result.equals("105")) {
            return "内容过多";
        }
        else if (result.equals("106")) {
            return "号码过多";
        }
        else if (result.equals("107")) {
            return "频率过快";
        }
        else if (result.equals("108")) {
            return "号码内容空";
        }
        if (result.equals("109")) {
            return "账号冻结";
        }
        else if (result.equals("110")) {
            return "禁止频繁单条发送";
        }
        else if (result.equals("111")) {
            return "系统暂定发送";
        }
        else if (result.equals("112")) {
            return "号码不正确";
        }
        else if (result.equals("120")) {
            return "系统升级";
        }
        if (result.equals("161")) {
            return "未添加短信模板";
        }
        else if (result.equals("162")) {
            return "模板格式不正确";
        }
        if (result.equals("163")) {
            return "模板ID不正确";
        }
        else if (result.equals("164")) {
            return "全文模板不匹配";
        }else {
            return null;
        }

    }

}
