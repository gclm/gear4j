package club.gclmit.chaos.logger.filter;

import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.JsonUtils;
import club.gclmit.chaos.core.lang.log.Logger;
import club.gclmit.chaos.core.lang.log.LoggerServer;
import club.gclmit.chaos.core.web.servlet.HttpServletUtils;
import club.gclmit.chaos.core.web.servlet.RequestWrapper;
import club.gclmit.chaos.core.web.servlet.ResponseWrapper;
import club.gclmit.chaos.logger.mapper.LoggerMapper;
import club.gclmit.chaos.logger.model.ChaosLoggerProperties;
import club.gclmit.chaos.logger.model.HttpTrace;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 日志 Filter
 * </p>
 *
 * @author gclm
 * @since 1.4
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
                LoggerMapper loggerMapper = genBean(LoggerMapper.class, request);
                boolean save = SQLUtils.retBool(loggerMapper.insert(trace));
                Logger.info(LoggerServer.CHAOS, "当前请求日志：{}\t入库：{}", trace, save);
            } else {
                Logger.info(LoggerServer.CHAOS, "当前请求日志：{}", trace);
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
        if (uri.startsWith(config.getPrefix()) || isIgnore(Arrays.asList(config.getIgnoreUrls()), uri)) {
            return false;
        }
        return true;
    }

    /**
     *  判断url 是否忽略
     *
     * @author gclm
     * @param uri  判断的url
     * @param ignoreUrls 忽略urls
     * @return boolean 如果是返回true,否则返回 false
     */
    public static boolean isIgnore(List<String> ignoreUrls, String uri){
        for (String ignoreUrl : ignoreUrls) {
            AntPathMatcher matcher = new AntPathMatcher();
            return matcher.match(ignoreUrl,uri);
        }
        return false;
    }
}
