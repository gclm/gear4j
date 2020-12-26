package club.gclmit.chaos.web.config;

import club.gclmit.chaos.core.util.MapUtils;
import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.web.annotation.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * 自定义查询解析器
 *
 * @author gclm
 */
@Slf4j
public class QueryHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Query.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Query query = parameter.getParameterAnnotation(Query.class);
        if (query == null) {
            throw new IllegalArgumentException(
                    "Unknown parameter type [" + parameter.getParameterType().getName() + "]");
        }

        Class clazz = query.clazz();
        log.info("chaos --> 当前转换Class:[{}]",clazz.getName());

        Map<String, String[]> params = webRequest.getParameterMap();
        Object obj = MapUtils.mapToObject(params, clazz);

        log.info("chaos --> 解析后Object:[{}]", StringUtils.toString(obj));

        return obj;
    }
}

