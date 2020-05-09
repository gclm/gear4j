package club.gclmit.chaos.core.text;

import club.gclmit.chaos.core.util.ObjectUtils;
import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.file.FileUtils;
import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.util.StringUtils;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  MD5工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/30 7:57 PM
 * @version: V1.0
 * @since 1.8
 */
public class Md5Utils {

    /**
     * 默认为 UTF-8的编码格式加密
     *
     * @throws
     * @author gclm
     * @param: str
     * @date 2020/3/31 2:52 PM
     * @return: java.lang.String
     */
    public static String encode(String str) {
        Assert.isFalse(StringUtils.isEmpty(str),"str 不能为空");
        return encode(str, null);
    }

    /**
     * 自定义编码格式加密
     *
     * @throws
     * @author gclm
     * @param: str     内容
     * @param: charset 编码格式
     * @date 2020/3/31 2:52 PM
     * @return: java.lang.String
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
     * md5加密（byte[] -> String）
     *
     * @author gclm
     * @param: data
     * @date 2020/5/6 5:34 下午
     * @return: java.lang.String
     */
    public static String encode(byte[] bytes) {
        Assert.isFalse(ObjectUtils.isEmpty(bytes),"bytes 不能为空");
        MessageDigest digest = md5();
        return getMd5Checksum(digest.digest(bytes));
    }

    /**
     * md5加密（File -> String）
     *
     * @author gclm
     * @param: file
     * @date 2020/3/31 2:56 PM
     * @return: java.lang.String
     */
    public static String encode(File file){
        Assert.isFalse(FileUtils.isEmpty(file),"文件不能为空");
        try (InputStream in = new FileInputStream(file)){
            return getMd5Checksum(encode(in));
        } catch (IOException e) {
            throw new ChaosCoreException("file to FileInputStream 失败",e);
        }
    }

    /**
     * md5加密（InputStream -> byte[]）
     *
     * @author gclm
     * @param: in
     * @date 2020/3/31 2:56 PM
     * @return: java.lang.String
     */
    public static byte[] encode(InputStream in){
        Assert.isFalse(IOUtils.isEmpty(in),"输入流 in 不能为空");
        DigestInputStream md5Stream = new DigestInputStream(in, md5());
        return md5Stream.getMessageDigest().digest();
    }

    /**
     * 16 进制转换
     *
     * @author gclm
     * @param: bytes
     * @return: java.lang.String
     */
    public static String getMd5Checksum(byte[] bytes){
        Assert.isFalse(ObjectUtils.isEmpty(bytes),"bytes 不能为空");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            sb.append(Integer.toHexString((bytes[i] & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }

    /**
     * 获取 md5 摘要
     *
     * @author gclm
     * @return: MessageDigest
     */
    public static MessageDigest md5(){
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new ChaosCoreException("获取md5 摘要失败",e);
        }
    }
}
