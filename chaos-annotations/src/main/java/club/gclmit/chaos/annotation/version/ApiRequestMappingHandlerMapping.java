package club.gclmit.chaos.annotation.version;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>
 *  重写RequestMappingHandlerMapping，使其支持版本控制
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/17 12:46 下午
 * @version: V1.0
 * @since 1.8
 */
public class ApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    /**
     *  扫描类上的 @ApiVersion
     *
     * @author gclm
     * @param: handlerType
     * @date 2019/12/17 1:16 下午
     * @return: org.springframework.web.servlet.mvc.condition.RequestCondition<?>
     * @throws
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion version = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createRquestCodition(version);
    }

    /**
     *  扫描方法上的@ApiVersion
     *
     * @author gclm
     * @param: method
     * @date 2019/12/17 1:15 下午
     * @return: org.springframework.web.servlet.mvc.condition.RequestCondition<?>
     * @throws
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createRquestCodition(apiVersion);
    }

    private RequestCondition<ApiVersionCondition> createRquestCodition(ApiVersion version) {
        if (Objects.isNull(version)) {
            return null;
        }
        int versionValue = version.value();
        Assert.isTrue(versionValue >= 1,"Api 版本不能小于 1");
        return new ApiVersionCondition(versionValue);
    }
}
