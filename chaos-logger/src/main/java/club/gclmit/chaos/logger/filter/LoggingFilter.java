package club.gclmit.chaos.logger.filter;

import club.gclmit.chaos.core.net.web.RequestWrapperCache;
import club.gclmit.chaos.core.net.web.ResponseWrapperCache;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
@WebFilter(filterName = "loggingFilter", urlPatterns = "/*")
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        RequestWrapperCache requestWrapper = new RequestWrapperCache((HttpServletRequest) request);
        ResponseWrapperCache responseWrapper = new ResponseWrapperCache((HttpServletResponse) response) ;

        chain.doFilter(requestWrapper, responseWrapper);
        responseWrapper.flushBuffer();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
