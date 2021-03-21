package club.gclmit.chaos.web.config;

import club.gclmit.chaos.web.annotation.FastApi;
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
public class FastApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    /**
     *  扫描类上的 @ApiVersion
     *
     * @author gclm
     * @param handlerType  Class
     * @return: RequestCondition
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        FastApi version = AnnotationUtils.findAnnotation(handlerType, FastApi.class);
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
        FastApi fastApi = AnnotationUtils.findAnnotation(method, FastApi.class);
        return createRequestCondition(fastApi);
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
    private RequestCondition<FastApiCondition> createRequestCondition(FastApi version) {

        if (Objects.isNull(version)) {
            return null;
        }
        int versionValue = version.value();
        Assert.isTrue(versionValue >= 1,"Api 版本不能小于 1");
        return new FastApiCondition(versionValue);
    }
}
