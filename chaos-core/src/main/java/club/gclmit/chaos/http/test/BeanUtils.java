package club.gclmit.chaos.http.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Spring BeanUtils
 * </p>
 * @author gclm
 */
public class BeanUtils {

    private BeanUtils() {
    }

    /**
     * 获取Bean对象
     *
     * @author gclm
     * @param clazz 获取 bean 对象
     * @param <T>  泛型
     * @param request request 请求
     * @return: T
     */
    public static <T> T genBean(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
