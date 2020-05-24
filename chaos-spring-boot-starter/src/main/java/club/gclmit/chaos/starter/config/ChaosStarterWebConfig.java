package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.file.FileUtils;
import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.starter.properties.ChaosWebConfig;
import club.gclmit.chaos.web.xss.XssFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;

/**
 * <p>
 *  MultipartResolver 配置
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/18 6:24 下午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
public class ChaosStarterWebConfig  implements WebMvcConfigurer {

    @Autowired
    private ChaosWebConfig config;

    /**
     * 文件上传临时路径
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(FileUtils.getRootPath());
        return factory.createMultipartConfig();
    }

    /**
     *  开启 knife4j（Swagger） 映射
     *
     * @author gclm
     * @param: registry
     * @date 2020/1/3 3:06 下午
     * @throws
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        //开启动态的xss开关
        XssFilter xssFilter = new XssFilter(() -> config.getEnableXss());
        FilterRegistrationBean registration = new FilterRegistrationBean(xssFilter);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * 增加 Cors 跨域支持
     * @author 孤城落寞
     * @date: 2019-08-27
     * @return: void
     */
    @SuppressWarnings({"rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean corsFilter() {
        Logger.info(LoggerServer.CHAOS, "增加 Cors 跨域支持");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}
