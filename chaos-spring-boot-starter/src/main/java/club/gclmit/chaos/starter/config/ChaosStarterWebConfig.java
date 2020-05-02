package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.starter.properties.ChaosWebConfig;
import club.gclmit.chaos.web.xss.XssFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.DispatcherType;

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

    @Value("${server.port}")
    private String port;

    @Autowired
    private ChaosWebConfig config;

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
        Logger.info(LoggerServer.CHAOS,"开启 Swagger 映射\nAPI文档地址：http://localhost:{}/doc.html",port);
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
     *  MultipartResolver 配置
     *  commons upload 上传配置
     *
     * @author gclm
     * @date 2020/4/30 2:21 上午
     * @return: org.springframework.web.multipart.MultipartResolver
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        Logger.info(LoggerServer.CHAOS, "MultipartResolver 配置，开启文件上传");
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        //上传文件大小 50M 50*1024*1024
        resolver.setMaxUploadSize(50*1024*1024L);
        return resolver;
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
