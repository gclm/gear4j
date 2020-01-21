package club.gclmit.chaos.security.core;

import club.gclmit.chaos.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 配置 SecuityProperties 生效
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/6 10:12 上午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
