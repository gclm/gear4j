package club.gclmit.chaos.logger.interceptor;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.core.net.web.HttpRequestUtils;
import club.gclmit.chaos.core.net.web.UrlUtils;
import club.gclmit.chaos.core.util.*;
import club.gclmit.chaos.logger.ChaosLoggerProperties;
import club.gclmit.chaos.logger.mapper.LoggerMapper;
import club.gclmit.chaos.logger.pojo.HttpTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * <p>
 * 日志拦截器
 * 1. 请求之前拦截
 * 2. 请求之后拦截
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/20 5:17 下午
 * @version: V1.0
 * @since 1.8
 */
public class LoggerInterceptor implements HandlerInterceptor {

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (checkIgnoreUrl(uri)) {
            return true;
        }

        /**
         * 如果 content Type 不存在默认为 application/x-www-form-urlencoded
         */
        String contentType = StringUtils.isEmpty(request.getContentType()) ? DEFAULT_CONTENT_TYPE : request.getContentType();
        Long requestTime = DateUtils.getMilliTimestamp();

        HttpTrace.HttpTraceBuilder builder = HttpTrace.builder()
                .uri(uri)
                .clientIp(HttpRequestUtils.getClientIp(request))
                .contentType(contentType)
                .method(request.getMethod())
                .userAgent(HttpRequestUtils.getUserAgent(request))
                .sessionId(HttpRequestUtils.getSessionId(request))
                .requestHeader(JsonUtils.toJson(HttpRequestUtils.getRequestHeaders(request)))
                .requestTime(requestTime)
                .requestBody(HttpRequestUtils.getRequestBody(request));

        request.setAttribute("HttpTrace",builder);
        request.setAttribute("requestTime",requestTime);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        HttpTrace.HttpTraceBuilder builder = (HttpTrace.HttpTraceBuilder) request.getAttribute("HttpTrace");
        Long requestTime = (Long) request.getAttribute("requestTime");

        /**
             *  获取 response 相关参数
             *  请求耗时 = 响应时间 - 请求时间
             */
            Long responseTime = DateUtils.getMilliTimestamp();
            Long time = responseTime - requestTime;

            HttpTrace trace = builder.httpCode(response.getStatus())
                    .responseBody(HttpRequestUtils.getResponseBody(response))
                    .responseHeader(JsonUtils.toJson(HttpRequestUtils.getResponseHeaders(response)))
                    .responseTime(responseTime)
                    .consumingTime(time).build();

            /**
             * 保存到数据库
             */
            if (config.isWriteDB()) {
                LoggerMapper loggerMapper = BeanUtils.genBean(LoggerMapper.class, request);
                boolean save = DbUtils.retBool(loggerMapper.insert(trace));
                Logger.info(LoggerServer.CHAOS, "当前请求日志入库：{}", save);
            } else {
                Logger.info(LoggerServer.CHAOS, "当前请求日志：{}", trace);
            }
    }

    private boolean checkIgnoreUrl(String uri) {
        if (uri.startsWith(config.getPrefix()) || !UrlUtils.isIgnore(Arrays.asList(config.getIgnoreUrls()), uri)) {
            return false;
        }
        return true;
    }
}
