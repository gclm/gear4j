package club.gclmit.chaos.core.net.web;

import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * HttpRequest 工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/2 2:31 下午
 * @version: V1.0
 * @since 1.8
 */
public class HttpRequestUtils {

    /**
     * Http 魔法值
     */
    private static final String UNKNOWN  = "UNKNOWN";

    /**
     * get request ip address
     *
     * @param request http request instance
     * @return ip address
     */
    public static String getClientIp(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * get all request header
     *
     * @param request http request instance
     * @return map
     */
    public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        Map<String, String> headers = new HashMap();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }

    /**
     * get all response header
     *
     * @param response http response instance
     * @return map
     */
    public static Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Assert.notNull(response, "response instance is null.");
        Map<String, String> headers = new HashMap();
        Iterator<String> iterator = response.getHeaderNames().iterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next();
            String headerValue = response.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }

    /**
     * get request header by name
     *
     * @param request    http request instance
     * @param headerName header name
     * @return header value
     */
    public static String getHeader(HttpServletRequest request, String headerName) {
        Assert.notNull(request, "request instance is null.");
        Assert.notNull(headerName, "request header name is null.");
        return request.getHeader(headerName);
    }

    /**
     * get request path param
     *
     * @param request http request instance
     * @return path param
     */
    public static Map getPathParams(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

    /**
     * get request uri
     *
     * @param request http request instance
     * @return request uri
     */
    public static String getUri(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        return request.getRequestURI();
    }

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
        Assert.notNull(request, "request instance is null.");
        return getHeader(request,"X-Requested-With");
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
        Assert.notNull(request, "request instance is null.");
        return getHeader(request,"User-Agent");
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
    public static String getRequestBody(HttpServletRequest request) {
        Assert.notNull(request, "request instance is null.");
        RequestWrapperCache requestWrapper;
        if (request instanceof RequestWrapperCache) {
            requestWrapper = (RequestWrapperCache) request;
        } else {
            requestWrapper = new RequestWrapperCache(request);
        }
        return requestWrapper.getBody();
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
    public static String getResponseBody(HttpServletResponse response) {
        Assert.notNull(response, "response instance is null.");
        ResponseWrapperCache responseWrapper;
        if (response instanceof ResponseWrapperCache) {
            responseWrapper = (ResponseWrapperCache) response;
        } else {
            responseWrapper = new ResponseWrapperCache(response);
        }
        return responseWrapper.getBody();
    }
}
