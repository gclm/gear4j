package club.gclmit.chaos.security.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.Handler;
import java.time.Clock;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/3 4:16 下午
 * @version: V1.0
 * @since 1.8
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("TimeInterceptor  preHandle");

        System.out.println("方法类：" + ((HandlerMethod)handler).getBean().getClass().getName());
        System.out.println("方法名：" + ((HandlerMethod) handler).getMethod().getName());

        request.setAttribute("startTime", Clock.systemDefaultZone().millis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("TimeInterceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("TimeInterceptor afterCompletion start ");
        System.out.println("TimeInterceptor 耗时：" + (Clock.systemDefaultZone().millis() - (Long) request.getAttribute("startTime")));
        System.out.println("TimeInterceptor afterCompletion end ");
    }
}
