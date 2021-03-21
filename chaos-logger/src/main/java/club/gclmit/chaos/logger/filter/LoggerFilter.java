package club.gclmit.chaos.logger.filter;

import club.gclmit.chaos.core.http.servlet.HttpCacheRequestWrapper;
import club.gclmit.chaos.core.http.servlet.HttpCacheResponseWrapper;
import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.ServletUtils;
import club.gclmit.chaos.core.util.SQLUtils;
import club.gclmit.chaos.core.util.UrlUtils;
import club.gclmit.chaos.json.util.JsonUtils;
import club.gclmit.chaos.logger.mapper.LoggerMapper;
import club.gclmit.chaos.logger.model.ChaosLoggerProperties;
import club.gclmit.chaos.logger.model.HttpTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
 * @author gclm
 * @since 1.4
 */
@Slf4j
@WebFilter(filterName = "loggerFilter", urlPatterns = "/*")
public class LoggerFilter extends OncePerRequestFilter implements Ordered {

    private int order = Ordered.LOWEST_PRECEDENCE;

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
        String sessionId = ServletUtils.getSessionId(request);

        if (checkIgnoreUrl(uri) || ServletUtils.isFileUpload(request)) {
            chain.doFilter(request, response);
        } else {
            HttpCacheRequestWrapper httpCacheRequestWrapper = new HttpCacheRequestWrapper(request);
            HttpCacheResponseWrapper responseWrapper = new HttpCacheResponseWrapper(response);
            chain.doFilter(httpCacheRequestWrapper, responseWrapper);
            /**
             *  获取 response 相关参数
             *  请求耗时 = 响应时间 - 请求时间
             */
            Long responseTime = DateUtils.getMilliTimestamp();
            Long time = responseTime - requestTime;

            HttpTrace trace = HttpTrace.builder()
                    .uri(uri)
                    .clientIp(ServletUtils.getClientIp(request))
                    .contentType(ServletUtils.getContentType(request))
                    .method(request.getMethod())
                    .userAgent(ServletUtils.getUserAgent(request))
                    .sessionId(sessionId)
                    .httpCode(response.getStatus())
                    .requestTime(requestTime)
                    .responseTime(responseTime)
                    .consumingTime(time)
                    .responseHeader(JsonUtils.toJson(ServletUtils.getResponseHeaders(response)))
                    .requestHeader(JsonUtils.toJson(ServletUtils.getRequestHeaders(request)))
                    .requestBody(ServletUtils.getRequestBody(httpCacheRequestWrapper))
                    .responseBody(ServletUtils.getResponseBody(responseWrapper))
                    .build();

            /**
             * 保存到数据库
             */
            if (config.getSave()) {
                LoggerMapper loggerMapper = genBean(LoggerMapper.class, request);
                boolean save = SQLUtils.retBool(loggerMapper.insert(trace));
                log.info("当前请求日志：{}\t入库：{}", trace, save);
            } else {
                log.info("当前请求日志：{}", trace);
            }
        }
    }

    /**
     * 获取Bean对象
     *
     * @author gclm
     * @param clazz 获取 bean 对象
     * @param <T>  泛型
     * @param request request 请求
     * @return T
     */
    public static <T> T genBean(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }

    /**
     * <p>
     *  效验当前请求是否需要忽略
     * </p>
     *
     * @author gclm
     * @param uri 效验请求
     * @return boolean
     */
    private boolean checkIgnoreUrl(String uri) {
        if (uri.startsWith(config.getPrefix()) || UrlUtils.isIgnore(Arrays.asList(config.getIgnoreUrls()), uri)) {
            return false;
        }
        return true;
    }
}
