package club.gclmit.chaos.logger;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.core.net.HttpUtils;
import club.gclmit.chaos.core.net.IpUtils;
import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.DbUtils;
import club.gclmit.chaos.core.util.JsonUtils;
import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.logger.db.mapper.LoggerMapper;
import club.gclmit.chaos.logger.db.pojo.HttpTrace;
import club.gclmit.chaos.logger.db.pojo.HttpTraceBuilder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 默认请求内容类型
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * 忽略请求内容类型
     */
    private static final String IGNORE_CONTENT_TYPE = "multipart/form-data";

    @Autowired
    private ChaosLoggerProperties config;

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(request, response);
        recordLogger(new ContentCachingRequestWrapper(request), new ContentCachingResponseWrapper(response));
    }

    /**
     * 采用缓冲 request 和 response，获取数据保存入库
     *
     * @author gclm
     * @param: requestWrapper
     * @param: responseWrapper
     * @date 2020/4/30 1:36 上午
     * @return: java.util.concurrent.Future<java.lang.Boolean>
     * @since 1.4.9
     */
    public void recordLogger(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) {

        String uri = requestWrapper.getRequestURI();
        String contentType = requestWrapper.getContentType();
        Long requestTime = DateUtils.getMilliTimestamp();

        /**
         * 默认拦截 /api 开头的接口
         */
        if (uri.startsWith(config.getPrefix())) {

            /**
             * 如果 content Type 不存在默认为 application/x-www-form-urlencoded
             */
            if (StringUtils.isEmpty(contentType)) {
                contentType = DEFAULT_CONTENT_TYPE;
            }

            HttpTraceBuilder httpTraceBuilder = HttpTrace.builder()
                    .requestTime(requestTime)
                    .clientIp(IpUtils.getClientIp(requestWrapper))
                    .contentType(contentType)
                    .method(requestWrapper.getMethod())
                    .userAgent(HttpUtils.getUserAgent(requestWrapper))
                    .sessionId(HttpUtils.getSessionId(requestWrapper))
                    .requestHeader(JsonUtils.toJson(HttpUtils.getRequestHeaders(requestWrapper)));


            /**
             *  判断请求是否是get,如果是 get 从 request 里面获取，否则从 body 里面获取
             */
            if (!contentType.startsWith(IGNORE_CONTENT_TYPE)) {
                String requestBody = JsonUtils.toJson(requestWrapper.getParameterMap());
                if (StringUtils.isEmpty(requestBody) || "{}".equals(requestBody)) {
                    requestBody = HttpUtils.getRequestBody(requestWrapper);
                }
                httpTraceBuilder.requestBody(requestBody);
            }

            /**
             *  获取 response 相关参数
             *  请求耗时 = 响应时间 - 请求时间
             */
            Long responseTime = DateUtils.getMilliTimestamp();
            Long time = responseTime - requestTime;

            HttpTrace trace = httpTraceBuilder.httpCode(responseWrapper.getStatus())
                    .responseBody(HttpUtils.getResponseBody(responseWrapper))
                    .responseHeader(JsonUtils.toJson(HttpUtils.getResponseHeaders(responseWrapper)))
                    .responseTime(responseTime)
                    .consumingTime(time).build();

            /**
             * 保存到数据库
             */
            if (config.isWriteDB()) {
                LoggerMapper loggerMapper = genBean(LoggerMapper.class, requestWrapper);
                boolean save = DbUtils.retBool(loggerMapper.insert(trace));
                Logger.info(LoggerServer.CHAOS, "当前请求日志入库：{}", save);
            } else {
                Logger.info(LoggerServer.CHAOS, "当前请求日志：{}", trace);
            }
        }
    }

    /**
     * 获取Bean对象
     *
     * @throws
     * @author gclm
     * @param: clazz
     * @param: request
     * @date 2020/1/20 10:40 上午
     * @return: T
     */
    public static <T> T genBean(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }


}
