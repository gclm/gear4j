package club.gclmit.chaos.logger.filter;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.core.net.web.HttpServletUtils;
import club.gclmit.chaos.core.net.web.RequestWrapper;
import club.gclmit.chaos.core.net.web.ResponseWrapper;
import club.gclmit.chaos.core.net.web.UrlUtils;
import club.gclmit.chaos.core.util.*;
import club.gclmit.chaos.logger.mapper.LoggerMapper;
import club.gclmit.chaos.logger.model.ChaosLoggerProperties;
import club.gclmit.chaos.logger.model.HttpTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>
 * 日志 Filter
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/21 7:27 下午
 * @version: V1.0
 * @since 1.8
 */
@WebFilter(filterName = "loggerFilter", urlPatterns = "/*")
public class LoggerFilter extends OncePerRequestFilter implements Ordered {

    private int order = Ordered.LOWEST_PRECEDENCE - 8;

    @Override
    public int getOrder() {
        return order;
    }

    @Autowired
    private ChaosLoggerProperties config;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Long requestTime = DateUtils.getMilliTimestamp();
        String sessionId = HttpServletUtils.getSessionId(request);

        if (checkIgnoreUrl(uri) || HttpServletUtils.isFileUpload(request)) {
            chain.doFilter(request, response);
        } else {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            ResponseWrapper responseWrapper = new ResponseWrapper(response);
            chain.doFilter(requestWrapper, responseWrapper);
            /**
             *  获取 response 相关参数
             *  请求耗时 = 响应时间 - 请求时间
             */
            Long responseTime = DateUtils.getMilliTimestamp();
            Long time = responseTime - requestTime;

            HttpTrace trace = HttpTrace.builder()
                    .uri(uri)
                    .clientIp(HttpServletUtils.getClientIp(request))
                    .contentType(HttpServletUtils.getContentType(request))
                    .method(request.getMethod())
                    .userAgent(HttpServletUtils.getUserAgent(request))
                    .sessionId(sessionId)
                    .httpCode(response.getStatus())
                    .requestTime(requestTime)
                    .responseTime(responseTime)
                    .consumingTime(time)
                    .responseHeader(JsonUtils.toJson(HttpServletUtils.getResponseHeaders(response)))
                    .requestHeader(JsonUtils.toJson(HttpServletUtils.getRequestHeaders(request)))
                    .requestBody(HttpServletUtils.getRequestBody(requestWrapper))
                    .responseBody(HttpServletUtils.getResponseBody(responseWrapper))
                    .build();

            /**
             * 保存到数据库
             */
            if (config.getSaveLogger()) {
                LoggerMapper loggerMapper = BeanUtils.genBean(LoggerMapper.class, request);
                boolean save = DbUtils.retBool(loggerMapper.insert(trace));
                Logger.info(LoggerServer.CHAOS, "当前请求日志：{}\t入库：{}", trace, save);
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
