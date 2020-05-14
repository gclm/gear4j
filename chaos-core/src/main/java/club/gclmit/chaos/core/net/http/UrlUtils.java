package club.gclmit.chaos.core.net.http;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.util.CharsetUtils;
import club.gclmit.chaos.core.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * <p>
 * url工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/2 2:26 下午
 * @version: V.0
 * @since 1.8
 */
public class UrlUtils {

    // url encode
    //-----------------------------------------------------------------------

    /**
     * encode 默认编码 UTF-8
     *
     * @param url url
     * @return String
     */
    public static String encode(String url) {
        Assert.isTrue(StringUtils.isBlank(url),"url 不能为空");
        return encode(url,CharsetUtils.UTF_8);
    }

    /**
     * encode 自定义编码格式
     *
     * @param url
     * @param charset
     * @return String
     */
    public static String encode(String url, Charset charset) {
        Assert.isTrue(StringUtils.isBlank(url),"url 不能为空");
        Assert.isNull(charset,"charset 不能为空");
        return encode(url,charset.name());
    }

    /**
     * encode 自定义编码格式
     *
     * @param url
     * @param charset
     * @return String
     */
    public static String encode(String url,String charset) {
        Assert.isTrue(StringUtils.isBlank(url),"url 不能为空");
        Assert.isTrue(StringUtils.isBlank(charset),"charset 不能为空");
        try {
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {
            throw new ChaosCoreException(e,"URL编码失败,url:{}\t编码格式：{}",url,charset);
        }
    }

    // url decode
    //-----------------------------------------------------------------------

    /**
     * decode 默认为UTF-8
     *
     * @param url
     * @return String
     */
    public static String decode(String url) {
        Assert.isTrue(StringUtils.isBlank(url),"url 不能为空");
        return encode(url,CharsetUtils.UTF_8);
    }

    /**
     * decode 自定义编码格式
     *
     * @param url
     * @param charset
     * @return String
     */
    public static String decode(String url, Charset charset) {
        Assert.isTrue(StringUtils.isBlank(url),"url 不能为空");
        Assert.isNull(charset,"charset 不能为空");
        return encode(url,charset.name());
    }

    /**
     * decode 自定义编码格式
     *
     * @param url
     * @param charset
     * @return String
     */
    public static String decode(String url,String charset) {
        Assert.isTrue(StringUtils.isBlank(url),"url 不能为空");
        Assert.isTrue(StringUtils.isBlank(charset),"charset 不能为空");
        try {
            return URLDecoder.decode(url,charset);
        } catch (UnsupportedEncodingException e) {
            throw new ChaosCoreException(e,"URL解码失败,url:{}\t编码格式：{}",url,charset);
        }
    }

}
