package club.gclmit.chaos.web.annotation.impl;

import club.gclmit.chaos.web.annotation.ApiVersion;
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
 * @author gclm
 */
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    /**
     *  扫描类上的 @ApiVersion
     *
     * @author gclm
     * @param handlerType  Class
     * @return: RequestCondition
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion version = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createRequestCondition(version);
    }

    /**
     *  扫描方法上的@ApiVersion
     *
     * @author gclm
     * @param method  扫描方法
     * @return RequestCondition
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createRequestCondition(apiVersion);
    }

    /**
     * <p>
     *  创建 requestRequestCondition
     * </p>
     *
     * @author gclm
     * @param version ApiVersion
     * @return org.springframework.web.servlet.mvc.condition.RequestCondition<club.gclmit.chaos.annotation.version.ApiVersionCondition>
     */
    private RequestCondition<ApiVersionCondition> createRequestCondition(ApiVersion version) {

        if (Objects.isNull(version)) {
            return null;
        }
        int versionValue = version.value();
        Assert.isTrue(versionValue >= 1,"Api 版本不能小于 1");
        return new ApiVersionCondition(versionValue);
    }
}
