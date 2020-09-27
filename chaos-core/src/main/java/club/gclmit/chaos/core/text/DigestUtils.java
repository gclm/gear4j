package club.gclmit.chaos.core.text;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.io.file.FileUtils;
import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 签名工具类
 * </p>
 *
 * @author gclm
 */
public class DigestUtils{

    /**
     * 默认为 UTF-8的编码格式签名
     *
     * @param str 内容
     * @return java.lang.String
     * @author gclm
     */
    public static String md5(String str) {
        Assert.isFalse(StringUtils.isEmpty(str), "str 不能为空");
        return md5(str, null);
    }

    /**
     * 自定义编码格式签名
     *
     * @param str     内容
     * @param charset 编码格式
     * @author gclm
     * @return: java.lang.String
     */
    public static String md5(String str, String charset) {
        Assert.isFalse(StringUtils.isEmpty(str), "str 不能为空");
        if (StringUtils.isNotEmpty(charset)) {
            return md5(str.getBytes(Charset.forName(charset)));
        } else {
            return md5(str.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * md5签名（File To String）
     *
     * @param file File
     * @return java.lang.String
     * @author gclm
     */
    public static String md5(File file) {
        Assert.isFalse(FileUtils.isEmpty(file), "文件不能为空");
        try (InputStream in = new FileInputStream(file)) {
            return md5(in);
        } catch (IOException e) {
            throw new ChaosCoreException("md5签名（File To String）失败", e);
        }
    }

    /**
     * md5签名（byte[] To String）
     *
     * @param bytes 内容
     * @return java.lang.String
     * @author gclm
     */
    public static String md5(byte[] bytes) {
        Assert.isFalse(ArrayUtils.isEmpty(bytes), "bytes 不能为空");
        return DigestAlgorithm.digestAsHexString(DigestAlgorithm.MD5_ALGORITHM_NAME, bytes);
    }

    /**
     * md5签名（InputStream To String）
     *
     * @param in InputStream
     * @return java.lang.String
     * @author gclm
     */
    public static String md5(InputStream in) {
        Assert.isFalse(IOUtils.isEmpty(in), "输入流 in 不能为空");
        try {
            return DigestAlgorithm.digestAsHexString(DigestAlgorithm.MD5_ALGORITHM_NAME, in);
        } catch (IOException e) {
            throw new ChaosCoreException("md5签名（InputStream To String）失败", e);
        }
    }

    /**
     * 默认为 UTF-8的编码格式签名
     *
     * @param str 内容
     * @return java.lang.String
     * @author gclm
     */
    public static String sha1(String str) {
        Assert.isFalse(StringUtils.isEmpty(str), "str 不能为空");
        return sha1(str, null);
    }

    /**
     * 自定义编码格式签名
     *
     * @param str     内容
     * @param charset 编码格式
     * @author gclm
     * @return: java.lang.String
     */
    public static String sha1(String str, String charset) {
        Assert.isFalse(StringUtils.isEmpty(str), "str 不能为空");
        if (StringUtils.isNotEmpty(charset)) {
            return sha1(str.getBytes(Charset.forName(charset)));
        } else {
            return sha1(str.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * sha1签名（File To String）
     *
     * @param file File
     * @return java.lang.String
     * @author gclm
     */
    public static String sha1(File file) {
        Assert.isFalse(FileUtils.isEmpty(file), "文件不能为空");
        try (InputStream in = new FileInputStream(file)) {
            return sha1(in);
        } catch (IOException e) {
            throw new ChaosCoreException("sha1签名（File To String）失败", e);
        }
    }

    /**
     * sha1签名（byte[] To String）
     *
     * @param bytes 内容
     * @return java.lang.String
     * @author gclm
     */
    public static String sha1(byte[] bytes) {
        Assert.isFalse(ArrayUtils.isEmpty(bytes), "bytes 不能为空");
        return DigestAlgorithm.digestAsHexString(DigestAlgorithm.MD5_ALGORITHM_NAME, bytes);
    }

    /**
     * sha1签名（InputStream To String）
     *
     * @param in InputStream
     * @return java.lang.String
     * @author gclm
     */
    public static String sha1(InputStream in) {
        Assert.isFalse(IOUtils.isEmpty(in), "输入流 in 不能为空");
        try {
            return DigestAlgorithm.digestAsHexString(DigestAlgorithm.MD5_ALGORITHM_NAME, in);
        } catch (IOException e) {
            throw new ChaosCoreException("sha1签名（InputStream To String）失败", e);
        }
    }
}
