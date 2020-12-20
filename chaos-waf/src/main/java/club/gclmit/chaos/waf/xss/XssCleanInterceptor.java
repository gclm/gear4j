package club.gclmit.chaos.web.filter.xss;

import club.gclmit.chaos.web.domain.properties.XssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * xss 处理拦截器
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class XssCleanInterceptor extends HandlerInterceptorAdapter {

	private final XssProperties xssProperties;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 1. 非控制器请求直接跳出
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// 2. 没有开启
		if (!xssProperties.isEnabled()) {
			return true;
		}
		// 3. 处理 XssIgnore 注解
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		XssCleanIgnore xssCleanIgnore = ClassUtils.getAnnotation(handlerMethod, XssCleanIgnore.class);
		if (xssCleanIgnore == null) {
			XssHolder.setEnable();
		}
		return true;
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		XssHolder.remove();
	}
}
