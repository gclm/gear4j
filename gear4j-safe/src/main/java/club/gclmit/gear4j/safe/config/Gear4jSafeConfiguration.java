package club.gclmit.gear4j.safe.config;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;

import club.gclmit.gear4j.safe.core.SafeFilter;

/**
 * Xss 自定注入配置
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/4 15:38
 * @since jdk11
 */
@Configuration
@EnableConfigurationProperties(value = {Gear4jSafeProperties.class})
@ConditionalOnProperty(prefix = Gear4jSafeProperties.PREFIX, name = "enabled", havingValue = "true")
public class Gear4jSafeConfiguration {

    @Autowired
    private Gear4jSafeProperties properties;

    @Bean
    public FilterRegistrationBean<SafeFilter> safeFilterRegistration() {
        FilterRegistrationBean<SafeFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new SafeFilter());
        registration.setUrlPatterns(properties.getUrlPatterns());
        registration.setName("safeFilter");
        registration.setOrder(9999);
        registration.addInitParameter(Gear4jSafeProperties.CONFIG_NAME, JSONObject.toJSONString(properties));
        return registration;
    }
}
