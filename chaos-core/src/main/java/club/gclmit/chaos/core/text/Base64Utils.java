package club.gclmit.chaos.core.text;

import club.gclmit.chaos.core.util.ObjectUtils;
import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.lang.Assert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <p>
 * base64 工具类
 * </p>
 *
 * @author gclm
 */
public class Base64Utils{

    /**
     * base64 解码
     *
     * @author gclm
     * @param bytes 字节数组
     * @return byte[]
     */
    public static byte[] decode(byte[] bytes) {
        Assert.isFalse(ObjectUtils.isEmpty(bytes),"bytes 不能为空");
        return Base64.getDecoder().decode(bytes);
    }


    // 编码
    //==============================================================

    /**
     * 默认为 UTF-8的编码格式加密
     *
     * @author gclm
     * @param str 内容
     * @return java.lang.String
     */
    public static String encode(String str) {
        Assert.isFalse(StringUtils.isEmpty(str),"str 不能为空");
        return encode(str, null);
    }

    /**
     * 自定义编码格式加密
     *
     * @author gclm
     * @param str     内容
     * @param charset 编码格式
     * @return java.lang.String
     */
    public static String encode(String str, String charset) {
        Assert.isFalse(StringUtils.isEmpty(str),"str 不能为空");
        if (StringUtils.isNotEmpty(charset)) {
            return encode(str.getBytes(Charset.forName(charset)));
        } else {
            return encode(str.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * 编码
     *
     * @author gclm
     * @param bytes     内容
     * @return java.lang.String
     */
    public static String encode(byte[] bytes) {
        Assert.isFalse(ObjectUtils.isEmpty(bytes),"bytes 不能为空");
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 网络数据加密
     * eq: 网上图片 To base64
     *
     * @author gclm
     * @param url  网络内容
     * @return java.lang.String
     */
    public static String encode(URL url) {
        Assert.notNull(url,"url不能为空");
        try (ByteArrayOutputStream data = new ByteArrayOutputStream()){
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            InputStream in = connection.getInputStream();
            IOUtils.copy(in,data);
            in.close();
            return encode(data.toByteArray());
        } catch (IOException e) {
           throw new ChaosCoreException("获取网络图片发生异常",e);
        }
    }

    /**
     * eq: file To base64
     *
     * @author gclm
     * @param file 图片
     * @return java.lang.String
     */
    public static String encode(File file) {
        Assert.notNull(file,"file不能为空");
        try(FileInputStream in = new FileInputStream(file)){
            return encode(IOUtils.toByteArray(in));
        } catch (IOException e) {
            throw new ChaosCoreException("获取网络图片发生异常",e);
        }
    }
}
