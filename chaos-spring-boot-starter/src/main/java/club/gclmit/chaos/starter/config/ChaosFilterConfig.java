package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.web.xss.XssFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import javax.servlet.DispatcherType;

/**
 * Filter配置
 *
 * @author gclm
 */
@Configuration
public class ChaosFilterConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * xssFilter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean xssFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

    /**
     * 增加 Cors 跨域支持
     *
     * @author 孤城落寞
     * @return: FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        log.info("增加 Cors 跨域支持");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new FilterRegistrationBean(new CorsFilter(source));
    }
}
