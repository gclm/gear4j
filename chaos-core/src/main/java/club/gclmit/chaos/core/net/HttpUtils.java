package club.gclmit.chaos.core.net;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  Http 请求工具类
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-22 10:18:00
 * @version: V1.0
 */
public class HttpUtils {

    /**
     *  获取客户端请求方式
     *  eq:
     *   - ajax
     *   - form
     *   - websocket
     *
     * @author gclm
     * @param: request
     * @date 2020/1/11 9:35 下午
     * @return: java.lang.String
     * @throws
     */
    public static String getRequestType(HttpServletRequest request) {
        return request.getHeader("X-Requested-With");
    }

    /**
     *  获取Session Id
     *
     * @author gclm
     * @param: request
     * @date 2020/1/20 9:34 上午
     * @return: java.lang.String
     * @throws
     */
    public static String getSessionId(HttpServletRequest request) {
       return request.getSession().getId();
    }

    /**
     *  获取用户代理
     *
     * @author gclm
     * @param: request
     * @date 2020/1/20 10:38 上午
     * @return: java.lang.String
     * @throws
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

}

