package club.gclmit.chaos.core.encrypt;

import	java.io.ByteArrayOutputStream;
import club.gclmit.chaos.core.exception.ChaosCoreException;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import	java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
public class Base64Helper extends Codec {

    /**
     * base64 解码
     *
     * @author gclm
     * @param: src
     * @date 2020/3/31 2:57 PM
     * @return: byte[]
     * @throws
     */
    public byte[] decode(byte[] src) {
        return Base64.getDecoder().decode(src);
    }

    /**
     *  Base64 编码
     *
     * @author gclm
     * @param: data
     * @date 2020/3/31 2:58 PM
     * @return: java.lang.String
     * @throws
     */
    @Override
    public String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
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
    public String encode(URL url) {

        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
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
