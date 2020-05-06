package club.gclmit.chaos.core.text.encrypt;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.util.ArrayUtils;
import club.gclmit.chaos.core.util.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * @author: gclm
 * @date: 2020/3/31 10:46 AM
 * @version: V1.0
 * @since 1.8
 */
public class Base64Utils{

    /**
     * base64 解码
     *
     * @author gclm
     * @param: src
     * @date 2020/3/31 2:57 PM
     * @return: byte[]
     * @throws
     */
    public static byte[] decode(byte[] bytes) {
        Assert.isFalse(ArrayUtils.isEmpty(bytes),"bytes 不能为空");
        return Base64.getDecoder().decode(bytes);
    }


    // 编码
    //==============================================================

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
     * 编码
     *
     * @throws
     * @author gclm
     * @param: bytes     内容
     * @date 2020/3/31 2:52 PM
     * @return: java.lang.String
     */
    public static String encode(byte[] bytes) {
        Assert.isFalse(ArrayUtils.isEmpty(bytes),"bytes 不能为空");
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 网络数据加密
     * eq: 网上图片 --> base64
     *
     * @author gclm
     * @param: url
     * @date 2020/3/31 2:56 PM
     * @return: java.lang.String
     * @throws
     */
    public static String encode(URL url) {
        Assert.notNull(url,"url不能为空");
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
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
}
