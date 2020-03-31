package club.gclmit.chaos.core.helper;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 *  基于反射获取配置信息
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/15 1:07 下午
 * @version: V1.0
 * @since 1.8
 */
public class ObjectHelper {

    /**
     *  通过反射获取 Object 的 属性
     * @author gclm
     * @param: object
     * @date 2020/1/15 3:07 下午
     * @return: java.lang.String
     * @throws
     */
    public static String toString(Object object){
        return ReflectionToStringBuilder.toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     *  获取Bean对象
     *
     * @author gclm
     * @param: clazz
     * @param: request
     * @date 2020/1/20 10:40 上午
     * @return: T
     * @throws
     */
    public static  <T> T  genBean(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }

}
