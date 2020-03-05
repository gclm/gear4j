package club.gclmit.chaos.logger;

import club.gclmit.chaos.core.helper.ObjectHelper;
import club.gclmit.chaos.core.helper.TimeHelper;
import club.gclmit.chaos.core.helper.NetHelper;
import club.gclmit.chaos.core.helper.logger.Logger;
import club.gclmit.chaos.core.helper.logger.LoggerServer;
import club.gclmit.chaos.logger.exception.ChaosLoggerException;
import club.gclmit.chaos.logger.pojo.HttpTrace;
import club.gclmit.chaos.logger.service.LoggerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 日志分发拦截器
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/19 3:01 下午
 * @version: V1.0
 * @since 1.8
 */
public class LoggerDispatcherServlet extends DispatcherServlet {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 默认请求内容类型
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * 忽略请求内容类型
     */
    private static final String IGNORE_CONTENT_TYPE = "multipart/form-data";

    /**
     * 设置默认编码格式解决 中文乱码问题
     */
    private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

    @Autowired
    private ChaosLoggerProperties logger;

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String uri = request.getRequestURI();
        String contentType = request.getContentType();

        /**
         * 默认拦截 /api 开头的接口
         */
        if (uri.startsWith(logger.getPrefix())) {

            /**
             * 缓冲 request 和 response
             */
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

            /**
             * 1. 获取写入数据库的相关参数
             * 2. 拼接参数到 HttpTrace
             */
            Long requestTime = TimeHelper.toMillis();
            String clientIp =  NetHelper.getClientIp(requestWrapper);
            String userAgent = NetHelper.getUserAgent(requestWrapper);
            String sessionId = NetHelper.getSessionId(requestWrapper);

            String method = requestWrapper.getMethod();
            String requestHeader = objectMapper.writeValueAsString(getRequestHeaders(requestWrapper));

            /**
             * 如果 content Type 不存在默认为 application/x-www-form-urlencoded
             */
            if (StringUtils.isEmpty(contentType)) {
                contentType = DEFAULT_CONTENT_TYPE;
            }

            HttpTrace trace = new HttpTrace(clientIp,uri,contentType,method,sessionId,requestTime,requestHeader,userAgent);

            try {
                super.doDispatch(request,response);
            } finally {

                String requestStr = "";
                /**
                 *  判断请求是否是get,如果是 get 从 request 里面获取，否则从 body 里面获取
                 */
                if (!contentType.startsWith(IGNORE_CONTENT_TYPE)){
                    requestStr = objectMapper.writeValueAsString(requestWrapper.getParameterMap());
                    if (StringUtils.isEmpty(requestStr) || "{}".equals(requestStr)){
                        requestStr = getRequestBody(requestWrapper);
                    }
                }

                /**
                 *  获取 response 相关参数
                 */
                int status = responseWrapper.getStatus();
                String responseStr =  getResponseBody(responseWrapper);
                responseWrapper.copyBodyToResponse();
                String responseHeader =  objectMapper.writeValueAsString(getResponseHeaders(responseWrapper));

                /**
                 * new 一个响应时间计算，请求耗时
                 */
                Long responseTime = TimeHelper.toMillis();
                Long time = responseTime - requestTime;

                /**
                 * 拼接对象
                 */
                trace.setRequest(requestStr);
                trace.setResponse(responseStr);
                trace.setResponseHeader(responseHeader);

                trace.setConsumingTime(time);
                trace.setResponseTime(responseTime);
                trace.setHttpStatusCode(status);

                Logger.debug(LoggerServer.CHAOS_LOGGER,"当前请求的 HttpTrace :{}", ObjectHelper.toString(trace));

                /**
                 * 保存到数据库
                 */
                LoggerService loggerService = getMapper(LoggerService.class, requestWrapper);
                boolean save = loggerService.save(trace);
                Logger.info(LoggerServer.CHAOS_LOGGER,"当前请求日志，插入数据库：{}", save);
            }
        }  else {
            super.doDispatch(request,response);
        }
    }

    /**
     *  获取Bean对象
     *
     * @author gclm
     * @param: clazz
     * @param: request
     * @date 2020/1/20 10:40 上午
     * @return: T
     * @throws
     */
    private static  <T> T  getMapper(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }


    /**
     *  获取 request 的请求头
     *
     * @author gclm
     * @param: request
     * @date 2020/1/19 3:24 下午
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    private Map<String,Object> getRequestHeaders(HttpServletRequest request) {
        Map<String,Object> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName,request.getHeader(headerName));
        }
        return headers;
    }

    /**
     *  获取 response 的请求头
     *
     * @author gclm
     * @param: response
     * @date 2020/1/19 3:25 下午
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    private Map<String,Object> getResponseHeaders(HttpServletResponse response) {
        Map<String,Object> headers = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            headers.put(headerName,response.getHeader(headerName));
        }
        return  headers;
    }

    /**
     *  获取 requestBody 内容
     *  wrapper.getCharacterEncoding() 默认为 ISO-8859-1
     *
     * @author gclm
     * @param: wrapper
     * @date 2020/1/20 4:17 下午
     * @return: java.lang.String
     * @throws
     */
    private String getRequestBody(ContentCachingRequestWrapper wrapper) {
        String requestBody = "";
        if (wrapper != null) {
            try {
                requestBody = IOUtils.toString(wrapper.getContentAsByteArray(), DEFAULT_CHARACTER_ENCODING);
            } catch (IOException e) {
                throw new ChaosLoggerException("解析 Request 请求内容失败");
            }
        }
        return requestBody;
    }

    /**
     *  获取 ResponseBody 内容
     *  wrapper.getCharacterEncoding() 默认为 ISO-8859-1
     *
     * @author gclm
     * @param: wrapper
     * @date 2020/1/20 4:17 下午
     * @return: java.lang.String
     * @throws
     */
    private String getResponseBody(ContentCachingResponseWrapper wrapper) {
        String responseBody = "";
        if (wrapper != null) {
            try {
                responseBody = IOUtils.toString(wrapper.getContentAsByteArray(),DEFAULT_CHARACTER_ENCODING);
            } catch (IOException e) {
                throw new ChaosLoggerException("解析 Response 响应内容失败");
            }
        }
        return responseBody;
    }
}
