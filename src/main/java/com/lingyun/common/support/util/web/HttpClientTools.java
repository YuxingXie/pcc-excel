package com.lingyun.common.support.util.web;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientTools {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientTools.class);
    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, null);
    }
    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) {

        if (url == null) {
            logger.info("http url can not be empty!");
        }
        String respCtn = "";
        // 1、创建 client 实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 2、创建 post 实例
        HttpPost post = new HttpPost(url);

        if (headers != null && !headers.isEmpty()) {

            for (Map.Entry<String, String> e : headers.entrySet()) {
                Header header=new BasicHeader(e.getKey(), e.getValue());
                post.addHeader(header);
            }
        }

        ArrayList<NameValuePair> reqParams = null;
        if (params != null && !params.isEmpty()) {
            reqParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> e : params.entrySet()) {
                reqParams.add(new BasicNameValuePair(e.getKey(), e.getValue()));

            }
        }

        HttpResponse response = null;
        try {
            if (reqParams != null){
                // 3、设置 post 参数
                post.setEntity(new UrlEncodedFormEntity(reqParams, "UTF-8"));
            }

//            if (StringUtils.isNotBlank(base64Img)){
//                post.setEntity(new StringEntity(URLEncoder.encode(base64Img, "UTF-8")));
//            }


            // 4、发送请求
            response = httpclient.execute(post);
            respCtn = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            logger.info("Fail to connect to remote host [" + url + "]" + e);
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                }
            }
        }
        return respCtn;
    }

    /**
     * http get 请求
     *
     * @param url
     *            url
     *
     */
    public static String doGet(String url) {
        if (url == null || url.isEmpty()) {
            logger.info("http url can not be empty!");
        }
        String respCtn = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpResponse response = null;
            HttpGet get = new HttpGet(url);
            response = httpclient.execute(get);
            respCtn = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                }
            }
        }
        return respCtn;
    }
    /**
     * 获取客户端操作系统信息
     * 从http的header中获取到user-agent，然后利用正则表达式判断是哪一种操作系统
     *
     * 暂只匹配Win 7、WinXP、Win2003、Win2000、MAC、WinNT、Linux、Mac68k、Win9x、iOS、Android
     *
     * @param userAgent request.getHeader("user-agent")的返回值
     *
     * @author
     *
     */
    public static String getClientOS(String userAgent){
        String cos = "unknow os";
        Pattern p = Pattern.compile(".*(Windows NT 6\\.1).*");
//        Matcher m = p.matcher(userAgent);
        Matcher m ;
/*


        if(m.find()){
            cos = "Windows7";
            return cos;
        }
        p = Pattern.compile(".*(9x 4.90|Win9(5|8)|Windows 9(5|8)|95/NT|Win32|32bit).*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "Win9x";
            return cos;
        }
        p = Pattern.compile(".*(Windows NT 5\\.1|Windows XP).*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "WinXP";
            return cos;
        }

        p = Pattern.compile(".*(WinNT|WindowsNT).*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "WinNT";
            return cos;
        }
        p = Pattern.compile(".*(Windows NT 5\\.2).*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "Win2003";
            return cos;
        }

        p = Pattern.compile(".*(Win2000|Windows 2000|Windows NT 5\\.0).*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "Win2000";
            return cos;
        }
 */
        p = Pattern.compile(".*(Win).*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "Windows";
            return cos;
        }
        p = Pattern.compile(".*(iPhone|iPhone OS).*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "iOS";
            return cos;
        }

        p = Pattern.compile(".*(Android).*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "Android";
            return cos;
        }
        p = Pattern.compile(".*(Mac|apple|MacOS8).*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "macOS";
            return cos;
        }


        p = Pattern.compile(".*Linux.*");
        if(m.find()){
            cos = "Linux";
            return cos;
        }

        p = Pattern.compile(".*68k|68000.*");
        m = p.matcher(userAgent);
        if(m.find()){
            cos = "Mac68k";
            return cos;
        }



        return cos;
    }
}
