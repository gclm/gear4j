package club.gclmit.chaos.web.config;

import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * 增加 Cors Config 跨域支持
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/20 10:57 上午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 增加 Cors 跨域支持
     * @author 孤城落寞
     * @param registry
     * @date: 2019-08-27
     * @return: void
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
        Logger.info(LoggerServer.SPRING_BOOT,"开启 Cors 跨域支持");
    }
}
