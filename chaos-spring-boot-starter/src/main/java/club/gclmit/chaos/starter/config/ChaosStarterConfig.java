package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.http.file.FileUtils;
import club.gclmit.chaos.http.log.Logger;
import club.gclmit.chaos.http.log.LoggerServer;
import club.gclmit.chaos.starter.properties.ChaosWebProperties;
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
 * MultipartResolver 配置
 * </p>
 *
 * @author gclm
 */
@Configuration
public class ChaosStarterConfig implements WebMvcConfigurer {

    @Autowired
    private ChaosWebProperties properties;

    /**
     * 文件上传临时路径
     *
     * @return MultipartConfigElement
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(FileUtils.getRootPath());
        return factory.createMultipartConfig();
    }

    /**
     * 开启 knife4j（Swagger） 映射
     *
     * @param registry ResourceHandlerRegistry
     * @author gclm
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * xssFilter注册
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        //开启动态的xss开关
        XssFilter xssFilter = new XssFilter(() -> properties.getEnableXss());
        FilterRegistrationBean bean = new FilterRegistrationBean(xssFilter);
        bean.setDispatcherTypes(DispatcherType.REQUEST);
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
        bean.addUrlPatterns("/*");
        return bean;
    }

    /**
     * 增加 Cors 跨域支持
     *
     * @author 孤城落寞
     * @return: FilterRegistrationBean
     */
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
        return new FilterRegistrationBean(new CorsFilter(source));
    }

}
