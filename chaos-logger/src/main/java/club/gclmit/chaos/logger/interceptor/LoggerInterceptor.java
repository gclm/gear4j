package club.gclmit.chaos.logger.interceptor;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.core.net.web.HttpServletUtils;
import club.gclmit.chaos.core.net.web.UrlUtils;
import club.gclmit.chaos.core.util.*;
import club.gclmit.chaos.logger.model.ChaosLoggerProperties;
import club.gclmit.chaos.logger.mapper.LoggerMapper;
import club.gclmit.chaos.logger.model.HttpTrace;
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

    @Autowired
    private ChaosLoggerProperties config;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (checkIgnoreUrl(uri) || HttpServletUtils.isFileUpload(request)) {
            return true;
        }

        /**
         * 如果 content Type 不存在默认为 application/x-www-form-urlencoded
         */
        Long requestTime = DateUtils.getMilliTimestamp();

        HttpTrace.HttpTraceBuilder builder = HttpTrace.builder()
                .uri(uri)
                .clientIp(HttpServletUtils.getClientIp(request))
                .contentType(HttpServletUtils.getContentType(request))
                .method(request.getMethod())
                .userAgent(HttpServletUtils.getUserAgent(request))
                .sessionId(HttpServletUtils.getSessionId(request))
                .requestHeader(JsonUtils.toJson(HttpServletUtils.getRequestHeaders(request)))
                .requestTime(requestTime)
                .requestBody(HttpServletUtils.getRequestBody(request));

        request.setAttribute("HttpTrace", builder);
        request.setAttribute("requestTime", requestTime);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (!checkIgnoreUrl(request.getRequestURI()) && !HttpServletUtils.isFileUpload(request)) {
            HttpTrace.HttpTraceBuilder builder = (HttpTrace.HttpTraceBuilder) request.getAttribute("HttpTrace");
            Long requestTime = (Long) request.getAttribute("requestTime");

            /**
             *  获取 response 相关参数
             *  请求耗时 = 响应时间 - 请求时间
             */
            Long responseTime = DateUtils.getMilliTimestamp();
            Long time = responseTime - requestTime;

            HttpTrace trace = builder.httpCode(response.getStatus())
                    .responseBody(HttpServletUtils.getResponseBody(response))
                    .responseHeader(JsonUtils.toJson(HttpServletUtils.getResponseHeaders(response)))
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
    }

    private boolean checkIgnoreUrl(String uri) {
        if (uri.startsWith(config.getPrefix()) || UrlUtils.isIgnore(Arrays.asList(config.getIgnoreUrls()), uri)) {
            return false;
        }
        return true;
    }

}
