package club.gclmit.chaos.core.net;

import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.util.CharsetUtils;
import club.gclmit.chaos.core.util.JsonUtils;
import club.gclmit.chaos.core.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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


    /**
     * 获取 request 的请求头
     *
     * @throws
     * @author gclm
     * @param: request
     * @date 2020/1/19 3:24 下午
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> getRequestHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>(20);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    /**
     * 获取 response 的请求头
     *
     * @throws
     * @author gclm
     * @param: response
     * @date 2020/1/19 3:25 下午
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> getResponseHeaders(HttpServletResponse response) {
        Collection<String> headerNames = response.getHeaderNames();
        Map<String, Object> headers = new HashMap<>(20);
        for (String headerName : headerNames) {
            headers.put(headerName, response.getHeader(headerName));
        }
        return headers;
    }

    /**
     * 获取 requestBody 内容
     * wrapper.getCharacterEncoding() 默认为 ISO-8859-1
     *
     * @throws
     * @author gclm
     * @param: wrapper
     * @date 2020/1/20 4:17 下午
     * @return: java.lang.String
     */
    public static String getRequestBody(ContentCachingRequestWrapper wrapper) {
        String requestBody = "";
        if (wrapper != null) {
            requestBody = toString(wrapper.getContentAsByteArray(),wrapper.getCharacterEncoding());
        }
        return requestBody;
    }

    /**
     * 获取 ResponseBody 内容
     * wrapper.getCharacterEncoding() 默认为 ISO-8859-1
     *
     * @throws
     * @author gclm
     * @param: wrapper
     * @date 2020/1/20 4:17 下午
     * @return: java.lang.String
     */
    public static String getResponseBody(ContentCachingResponseWrapper wrapper) {
        String responseBody = "";
        if (wrapper != null) {
            responseBody = toString(wrapper.getContentAsByteArray(),wrapper.getCharacterEncoding());
        }
        return responseBody;
    }

    private static String toString(byte[] bytes,String charset){
         return StringUtils.toString(bytes, charset);
    }

}

