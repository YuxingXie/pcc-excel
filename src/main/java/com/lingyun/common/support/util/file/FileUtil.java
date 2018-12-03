package com.lingyun.common.support.util.file;


import com.lingyun.common.support.util.string.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-8
 * Time: 上午11:00
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil extends FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static void writeFile(String string, String filePath) throws IOException {
        String content = string;
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();

    }




    private static boolean isExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static InputStream getFileInputStream(String path) {
        try {
            if (isExist(path)) {
                return new FileInputStream(path);
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        return null;
    }


    /**
     * 把文本编码为Html代码
     *
     * @param target
     * @return 编码后的字符串
     */
    public static String htmEncode(String target) {
        StringBuilder sb = new StringBuilder();
        int j = target.length();
        for (int i = 0; i < j; i++) {
            char c = target.charAt(i);
            switch (c) {
                case 60:
                    sb.append("&lt;");
                    break;
                case 62:
                    sb.append("&gt;");
                    break;
                case 38:
                    sb.append("&amp;");
                    break;
                case 34:
                    sb.append("&quot;");
                    break;
                case 169:
                    sb.append("&copy;");
                    break;
                case 174:
                    sb.append("&reg;");
                    break;
                case 165:
                    sb.append("&yen;");
                    break;
                case 8364:
                    sb.append("&euro;");
                    break;
                case 8482:
                    sb.append("&#153;");
                    break;
                case 13:
                    if (i < j - 1 && target.charAt(i + 1) == 10) {
                        sb.append("<br>");
                        i++;
                    }
                    break;
                case 32:
                    if (i < j - 1 && target.charAt(i + 1) == ' ') {
                        sb.append(" &nbsp;");
                        i++;
                        break;
                    }
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }


    public static byte[] getBytesFromFile(File file) throws IOException {

        InputStream is = new FileInputStream(file);

// 获取文件大小

        long length = file.length();

        if (length > Integer.MAX_VALUE) {

            // 文件太大，无法读取

            throw new IOException("File is to large " + file.getName());

        }

// 创建一个数据来保存文件数据

        byte[] bytes = new byte[(int) length];

// 读取数据到byte数组中

        int offset = 0;

        int numRead = 0;

        while (offset < bytes.length

                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {

            offset += numRead;

        }

// 确保所有数据均被读取

        if (offset < bytes.length) {

            throw new IOException("Could not completely read file " + file.getName());

        }

// Close the input stream and return bytes

        is.close();

        return bytes;

    }

    /**
     * @param base64Str
     * @param toDir
     * @return 文件的新名称, 比如1231545612123_123.jpg
     */
    public static String saveBase64ToDirWithRandomName(String base64Str, String toDir) {

        if (base64Str.isEmpty()) return null;

        OutputStream outputStream = null;
        String[] baseStrs = base64Str.split(",");
        String suffix = (baseStrs[0].split("/")[1]).split(";")[0];//文件名后缀，即文件类型


//        String p = File.separator + childFolder + File.separator;

//        File f = new File(toDir, p);
        File toDirFile = new File(toDir);
        if (!toDirFile.exists()) {
            toDirFile.mkdirs();
        }
        try {
            //文件重命名
            Random rand = new Random();
            int a = rand.nextInt(100) + 1;
            String newFileName = System.currentTimeMillis() + "_" + a + "." + suffix;
            String newFilePath = toDir + File.separator + newFileName.trim();

            outputStream = new BufferedOutputStream(new FileOutputStream(new File(newFilePath)));

            logger.info(newFilePath);
//            BASE64Decoder decoder = new BASE64Decoder();

//            byte[] b = decoder.decodeBuffer(baseStrs[1]);
            byte[] b = Base64.decodeBase64(baseStrs[1]);

            outputStream.write(b);
            outputStream.flush();
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


    }


    /**
     *  本地图片转换成base64字符串
     *  @param imgFile	图片本地路径
     *  @return	 *	 * @author ZHANGJL
     *  @dateTime 2018-02-23 14:40:46
     */
    public static String imageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
        return Base64.encodeBase64String(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 在线图片转换成base64字符串
     * * 	 * @param imgURL	图片线上路径
     * * @return	 *	 * @author ZHANGJL	 * @dateTime 2018-02-23 14:43:18
     */
    public static String ImageToBase64ByOnline(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        return encoder.encode(data.toByteArray());
        return Base64.encodeBase64String(data.toByteArray());

    }


    /**
     * base64字符串转换成图片	 * @param imgStr		base64字符串	 * @param imgFilePath	图片存放路径	 * @return	 *	 * @author ZHANGJL	 * @dateTime 2018-02-23 14:42:17
     */
    public static boolean Base64ToImage(String imgStr, String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
        if (StringUtils.isBlank(imgStr)) // 图像数据为空
            return false;
//        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    // 调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }




    public static void main(String[] arg) {

        String base64=imageToBase64ByLocal("/Users/xulei/Library/Mobile Documents/com~apple~CloudDocs/Desktop/snapshot/2B5E7D24CDCE160C788469C0F980755B.jpg");
        logger.info(base64);

    }
}
