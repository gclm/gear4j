package club.gclmit.chaos.swagger;

import club.gclmit.chaos.core.helper.logger.Logger;
import club.gclmit.chaos.core.helper.logger.LoggerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 *  Swagger-Web项目配置
 *  @EnableWebMvc: 会覆盖掉SpringBoot的默认的静态资源映射配置
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/3 9:12 上午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${server.port}")
    private String port;

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
        Logger.info(LoggerServer.SPRING_BOOT,"开启 Swagger 映射\nAPI文档地址：http://localhost:{}/doc.html",port);
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

