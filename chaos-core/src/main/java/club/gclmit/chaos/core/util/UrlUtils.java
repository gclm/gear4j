package club.gclmit.chaos.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.BitSet;
import club.gclmit.chaos.core.exception.ChaosException;
import cn.hutool.core.lang.Assert;
import lombok.experimental.UtilityClass;

/**
 * Url工具类
 *
 * @author gclm
 * @since 12/21/2020 2:43 PM
 * @since 1.8
 */
@UtilityClass
public class UrlUtils {


    /**
     * 这里会有误差,比如输入一个字符串 123+456,它到底是原文就是123+456还是123 456做了urlEncode后的内容呢？<br>
     * 其实问题是一样的，比如遇到123%2B456,它到底是原文即使如此，还是123+456 urlEncode后的呢？ <br>
     * 在这里，我认为只要符合urlEncode规范的，就当作已经urlEncode过了<br>
     * 毕竟这个方法的初衷就是判断string是否urlEncode过<br>
     *
     * @return 返回不需要编码集合
     */
    public static BitSet getDontNeedEncoding() {
        BitSet dontNeedEncoding = new BitSet(256);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            dontNeedEncoding.set(i);
        }

        dontNeedEncoding.set('+');
        dontNeedEncoding.set('-');
        dontNeedEncoding.set('_');
        dontNeedEncoding.set('.');
        dontNeedEncoding.set('*');
        return dontNeedEncoding;
    }

    /**
     * 判断str是否urlEncoder.encode过<br>
     * 经常遇到这样的情况，拿到一个URL,但是搞不清楚到底要不要encode.<Br>
     * 不做encode吧，担心出错，做encode吧，又怕重复了<Br>
     *
     * @param str 待效验url
     * @return true url编码过
     */
    public static boolean hasUrlEncoded(String str) {
        /**
         * 支持JAVA的URLEncoder.encode出来的string做判断。 即: 将' '转成'+' <br>
         * 0-9a-zA-Z保留 <br>
         * '-'，'_'，'.'，'*'保留 <br>
         * 其他字符转成%XX的格式，X是16进制的大写字符，范围是[0-9A-F]
         */
        boolean needEncode = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (getDontNeedEncoding().get((int) c)) {
                continue;
            }
            if (c == '%' && (i + 2) < str.length()) {
                // 判断是否符合urlEncode规范
                char c1 = str.charAt(++i);
                char c2 = str.charAt(++i);
                if (isDigit16Char(c1) && isDigit16Char(c2)) {
                    continue;
                }
            }
            // 其他字符，肯定需要urlEncode
            needEncode = true;
            break;
        }

        return !needEncode;
    }

    /**
     * 判断c是否是16进制的字符
     *
     * @param c 待判断字符
     * @return true 是16进制
     */
    private static boolean isDigit16Char(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F');
    }

    // url encode
    //-----------------------------------------------------------------------

    /**
     * encode 默认编码 UTF-8
     *
     * @param url url
     * @return String
     */
    public static String encode(String url) {
        Assert.isTrue(StringUtils.isNotBlank(url), "url 不能为空");
        return encode(url, CharsetUtils.UTF_8);
    }

    /**
     * encode 自定义编码格式
     *
     * @param url     url
     * @param charset 编码格式
     * @return String
     */
    public static String encode(String url, Charset charset) {
        Assert.isTrue(StringUtils.isNotBlank(url), "url 不能为空");
        Assert.isNull(charset, "charset 不能为空");
        return encode(url, charset.name());
    }

    /**
     * encode 自定义编码格式
     *
     * @param url     url
     * @param charset 编码格式
     * @return String
     */
    public static String encode(String url, String charset) {
        Assert.isTrue(StringUtils.isNotBlank(url), "url 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(charset), "charset 不能为空");
        try {
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {
            throw new ChaosException(e, "URL编码失败,url:{}\t编码格式：{}", url, charset);
        }
    }

    // url decode
    //-----------------------------------------------------------------------

    /**
     * decode 默认为UTF-8
     *
     * @param url URL
     * @return String
     */
    public static String decode(String url) {
        Assert.isTrue(StringUtils.isNotBlank(url), "url 不能为空");
        return decode(url, CharsetUtils.UTF_8);
    }

    /**
     * decode 自定义编码格式
     *
     * @param url     url
     * @param charset 编码格式
     * @return String
     */
    public static String decode(String url, Charset charset) {
        Assert.isTrue(StringUtils.isNotBlank(url), "url 不能为空");
        Assert.isNull(charset, "charset 不能为空");
        return decode(url, charset.name());
    }

    /**
     * decode 自定义编码格式
     *
     * @param url     url
     * @param charset 编码格式
     * @return String
     */
    public static String decode(String url, String charset) {
        Assert.isTrue(StringUtils.isNotBlank(url), "url 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(charset), "charset 不能为空");
        try {
            return URLDecoder.decode(url, charset);
        } catch (UnsupportedEncodingException e) {
            throw new ChaosException(e, "URL解码失败,url:{}\t编码格式：{}", url, charset);
        }
    }


}
