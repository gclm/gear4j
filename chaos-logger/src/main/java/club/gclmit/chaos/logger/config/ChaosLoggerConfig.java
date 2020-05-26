package club.gclmit.chaos.logger.config;

import club.gclmit.chaos.logger.model.ChaosLoggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * chaos 日志模块配置
 * </p>
 *
 * @author gclm
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ChaosLoggerProperties.class)
public class ChaosLoggerConfig implements WebMvcConfigurer {

}
