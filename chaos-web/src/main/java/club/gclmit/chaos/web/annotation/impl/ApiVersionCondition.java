package club.gclmit.chaos.web.annotation.impl;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  重写 RequestCondition 组件
 * </p>
 *
 * @author gclm
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    private static final Pattern VERSION_PREFIX_PATTERN = Pattern.compile("/v(\\d+)/");

    private int apiVersion;

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        /**
         * 最近优先原则，方法定义 @ApiVersion > 类定义的 @ApiVersion
         */
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        Matcher matcher = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (matcher.find()) {
            /**
             *  获得符合匹配条件的 ApiVersionCodition
             */
            Integer version = Integer.valueOf(matcher.group(1));
            if (version >= getApiVersion()) {
                return this;
            }
        }
        return null;
    }

    /**
     *  当出现多个符合匹配条件的ApiVersionCondition， 优先匹配版本号较大的
     *
     * @author gclm
     * @param condition ApiVersionCondition
     * @param request HttpServletRequest
     */
    @Override
    public int compareTo(ApiVersionCondition condition, HttpServletRequest request) {
        return condition.getApiVersion() - getApiVersion();
    }

    public ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }
}

