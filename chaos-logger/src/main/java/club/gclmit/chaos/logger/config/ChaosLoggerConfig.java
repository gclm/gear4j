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
 * @author: gclm
 * @date: 2020/1/19 2:59 下午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ChaosLoggerProperties.class)
public class ChaosLoggerConfig implements WebMvcConfigurer {

}
