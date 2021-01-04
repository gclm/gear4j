package club.gclmit.chaos.core.codec;

import club.gclmit.chaos.core.util.CharsetUtils;
import lombok.experimental.UtilityClass;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Base64 工具类
 *
 * @author gclm
 */
@UtilityClass
public class Base64Utils {

    private static final Charset DEFAULT_CHARSET = CharsetUtils.CHARSET_UTF_8;

    /**
     * Base64对给定的字节数组进行编码
     * @param src   原始数据
     * @return      编码数据
     */
    public static byte[] encode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getEncoder().encode(src);
    }

    /**
     * Base64对给定的字节数组进行解码
     * @param src  编码数据
     * @return     原始数据
     */
    public static byte[] decode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getDecoder().decode(src);
    }

    /**
     * Base64 使用RFC 4648编码给定的字节数组
     * @param src 原始数据
     * @return    编码数据
     */
    public static byte[] encodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlEncoder().encode(src);
    }

    /**
     *  Base64 使用RFC 4648解码给定的字节数组
     * @param src 编码数据
     * @return 原始数据
     */
    public static byte[] decodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlDecoder().decode(src);
    }

    /**
     * Base64对给定的字节数组进行编码
     * @param src  原始数据
     * @return     UTF-8 编码数据
     */
    public static String encodeToString(byte[] src) {
        if (src.length == 0) {
            return "";
        }
        return new String(encode(src), DEFAULT_CHARSET);
    }

    /**
     * Base64对给定的字节数组进行解码
     * @param src UTF-8 编码数据
     * @return 原始数据
     */
    public static byte[] decodeFromString(String src) {
        if (src.isEmpty()) {
            return new byte[0];
        }
        return decode(src.getBytes(DEFAULT_CHARSET));
    }

    /**
     * base64编码,URL安全的
     * @param src 原始数据
     * @return UTF-8 编码数据
     */
    public static String encodeToUrlSafeString(byte[] src) {
        return new String(encodeUrlSafe(src), DEFAULT_CHARSET);
    }

    /**
     * Base64-使用RFC 4648从UTF解码给定的字节数组，URL安全
     * @param src 编码数据
     * @return 原始数据
     */
    public static byte[] decodeFromUrlSafeString(String src) {
        return decodeUrlSafe(src.getBytes(DEFAULT_CHARSET));
    }

}
