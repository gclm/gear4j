package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
public class MultipartConfig  implements WebMvcConfigurer {

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        Logger.info(LoggerServer.SPRING_BOOT, "MultipartResolver 配置，开启文件上传");
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        //上传文件大小 50M 50*1024*1024
        resolver.setMaxUploadSize(50*1024*1024);
        return resolver;
    }
}
