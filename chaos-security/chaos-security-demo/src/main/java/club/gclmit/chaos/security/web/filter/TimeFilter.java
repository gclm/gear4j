package club.gclmit.chaos.security.web.filter;

import	java.time.Clock;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * <p>
 *  时间拦截器
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/2 21:26
 * @version: V1.0
 * @since 1.8
 */
//@Component
public class TimeFilter  implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init ...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("time filter start ...");
        long startTime = Clock.systemUTC().millis();
        chain.doFilter(request, response);
        System.out.println("time filter  耗时: " + (Clock.systemUTC().millis() - startTime));
        System.out.println("time filter end ...");
    }

    @Override
    public void destroy() {
        System.out.println("time filter destroy ...");
    }
}
