package com.lingyun.common.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsConfig {
    @Value(value = "${app.sms.addr}")
    private String addr;
    //    private static final String addr = "\"http://api.sms.cn/mtutf8";
    @Value(value = "${app.sms.userId}")
    private String userId;

    /*
     * 如uid是：test，登录密码是：123123
     * 加密后：则加密串为  md5(123123test)=b9887c5ebb23ebb294acab183ecf0769
     *
     * 可用在线生成地址：http://www.sms.cn/password
     */
    @Value(value = "${app.sms.pwd}")
    private String pwd ;
    //5dcd15557f05314072ad9f6e94b408d7
    @Value(value = "${app.sms.encode}")
    private String encode;

    //宁乡政府的sign
    @Value(value = "${app.sms.sign}")
    private String sign;


//    @Value(value = "${app.sms.siginup.valid.code.template}")
//    /**
//     * 手机注册短信模板编号
//     */
//    private String signUpValidCodeTemplate;
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }



    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
