package club.gclmit.chaos.core.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Spring BeanUtils
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/21 6:29 下午
 * @version: V1.0
 * @since 1.8
 */
public class BeanUtils {

    /**
     * 获取Bean对象
     *
     * @throws
     * @author gclm
     * @param: clazz
     * @param: request
     * @date 2020/1/20 10:40 上午
     * @return: T
     */
    public static <T> T genBean(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
