package club.gclmit.chaos.logger;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.logger.interceptor.LoggerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 *  chaos 日志模块配置
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/19 2:59 下午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ChaosLoggerProperties.class)
public class ChaosLoggerConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
                .addPathPatterns("/**");
    }

    @Deprecated
//    @Bean
    public ServletRegistrationBean dispatcherRegistration() {
        Logger.info(LoggerServer.CHAOS,"开始加载 dispatcherServlet 组件,默认拦截的 API 前缀为：/api");
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(dispatcherServlet());
        registrationBean.setLoadOnStartup(1);
        //指定name，如果不指定默认为dispatcherServlet
        registrationBean.setName("Logger");
        return registrationBean;
    }

    @Deprecated
//    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet(){
        return new LoggerDispatcherServlet();
    }
}
