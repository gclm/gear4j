package club.gclmit.chaos.core.util;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.internal.RealHttpResult;
import lombok.experimental.UtilityClass;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Http请求相关工具类
 * </p>
 *
 * @since 1.8
 */
@UtilityClass
public class HttpUtils {

    /**
     * 构建统一配置的 HTTP 客户端
     *
     * @return com.ejlchina.okhttps.HTTP
     * @author gclm
     */
    public static HTTP buildHttp() {
        return HTTP.builder()
                .config((OkHttpClient.Builder builder) -> {
                    // 配置连接池 最小10个连接（不配置默认为 5）
                    builder.connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES));
                    // 配置连接超时时间（默认10秒）
                    builder.connectTimeout(20, TimeUnit.SECONDS);
                    // 其它配置: CookieJar、SSL、缓存、代理、事件监听...
                }).build();
    }

    /**
     * <p>
     * 效验链接。 code == 200 ? true : false
     * </p>
     *
     * @param url 请求url
     * @return boolean
     * @author gclm
     */
    public static boolean judgeUrl(String url) {
        return 200 == getHttpStatus(url);
    }

    /**
     * <p>
     * 获取请求url的状态码
     * </p>
     *
     * @param url 请求url
     * @return int 状态码
     * @author gclm
     */
    public static int getHttpStatus(String url) {
        return buildHttp().async(url).addHeader(requestHeader()).get().getResult().getStatus();
    }

    /**
     * <p>
     * 链接 ping
     * </p>
     *
     * @param url 请求url
     * @return java.lang.Long
     * @author gclm
     */
    public static Long ping(String url) {
        HttpResult result = buildHttp().async(url).addHeader(requestHeader()).get().getResult();
        Response response = ((RealHttpResult) result).getResponse();
        long responseAtMillis = response.receivedResponseAtMillis();
        long sentRequestAtMillis = response.sentRequestAtMillis();
        return responseAtMillis - sentRequestAtMillis;
    }

    /**
     * <p>
     * 通用请求头
     * </p>
     *
     * @return java.util.Map
     * @author gclm
     */
    public static Map<String, String> requestHeader() {
        Map<String, String> header = new HashMap<>(4);
        header.put("Cache-Control", "no-cache");
        header.put("Accept", "*/*");
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
        return header;
    }

}
