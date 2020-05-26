package club.gclmit.chaos.core.text;

import club.gclmit.chaos.core.util.ArrayUtils;
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
 * 加密工具类
 * </p>
 *
 * @author gclm
 */
public class DigestUtils extends org.springframework.util.DigestUtils {

    /**
     * 默认为 UTF-8的编码格式加密
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
     * 自定义编码格式加密
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
     * md5加密（byte[] To String）
     *
     * @param bytes 内容
     * @return java.lang.String
     * @author gclm
     */
    public static String md5(byte[] bytes) {
        Assert.isFalse(ArrayUtils.isEmpty(bytes), "bytes 不能为空");
        return md5DigestAsHex(bytes);
    }

    /**
     * md5加密（File To String）
     *
     * @param file File
     * @return java.lang.String
     * @throws IOException if an I/O error occurs
     * @author gclm
     */
    public static String md5(File file) {
        Assert.isFalse(FileUtils.isEmpty(file), "文件不能为空");
        try (InputStream in = new FileInputStream(file)) {
            return md5(in);
        } catch (IOException e) {
            throw new ChaosCoreException("md5加密（File To String）失败", e);
        }
    }

    /**
     * md5加密（InputStream To String）
     *
     * @param in InputStream
     * @return java.lang.String
     * @throws IOException if an I/O error occurs
     * @author gclm
     */
    public static String md5(InputStream in) {
        Assert.isFalse(IOUtils.isEmpty(in), "输入流 in 不能为空");
        try {
            return md5DigestAsHex(in);
        } catch (IOException e) {
            throw new ChaosCoreException("md5加密（InputStream To String）失败", e);
        }
    }
}
