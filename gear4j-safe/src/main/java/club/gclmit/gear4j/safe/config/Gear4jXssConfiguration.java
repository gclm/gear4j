package club.gclmit.gear4j.safe.config;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;

import club.gclmit.gear4j.safe.xss.XssFilter;

/**
 * TODO
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/4 15:38
 * @since jdk11
 */
@Configuration
@EnableConfigurationProperties(value = {Gear4jXssProperties.class})
@ConditionalOnProperty(prefix = Gear4jXssProperties.PREFIX, name = "enabled", havingValue = "true")
public class Gear4jXssConfiguration {

    @Autowired
    private Gear4jXssProperties properties;

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.setUrlPatterns(properties.getUrlPatterns());
        registration.setName("xssFilter");
        registration.setOrder(9999);
        registration.addInitParameter("xssProperties", JSONObject.toJSONString(properties));
        return registration;
    }
}
