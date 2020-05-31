package club.gclmit.chaos.core.net.web;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.util.CharsetUtils;
import club.gclmit.chaos.core.util.StringUtils;
import lombok.experimental.UtilityClass;
import org.springframework.util.AntPathMatcher;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <p>
 * url工具类
 * </p>
 *
 * @author gclm
 */
@UtilityClass
public class UrlUtils {

    /**
     * <p>
     *  判断url 是否忽略
     * </p>
     *
     * @author gclm
     * @param uri  判断的url
     * @param ignoreUrls 忽略urls
     * @return boolean 如果是返回true,否则返回 false
     */
    public static boolean isIgnore(List<String> ignoreUrls,String uri){
        for (String ignoreUrl : ignoreUrls) {
            AntPathMatcher matcher = new AntPathMatcher();
            return matcher.match(ignoreUrl,uri);
        }
        return false;
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
        Assert.isTrue(StringUtils.isBlank(url),"url 不能为空");
        return encode(url,CharsetUtils.UTF_8);
    }

    /**
     * encode 自定义编码格式
     *
     * @param url     url
     * @param charset 编码格式
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
     * @param url     url
     * @param charset 编码格式
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
     * @param url URL
     * @return String
     */
    public static String decode(String url) {
        Assert.isTrue(StringUtils.isBlank(url),"url 不能为空");
        return encode(url,CharsetUtils.UTF_8);
    }

    /**
     * decode 自定义编码格式
     *
     * @param url     url
     * @param charset 编码格式
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
     * @param url     url
     * @param charset 编码格式
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
