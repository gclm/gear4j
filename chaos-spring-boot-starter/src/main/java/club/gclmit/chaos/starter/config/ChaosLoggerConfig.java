package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.logger.model.ChaosLoggerProperties;
import club.gclmit.chaos.starter.properties.ChaosProperties;
import club.gclmit.chaos.waf.xss.XssJacksonDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * chaos-storage 自动配置
 * </p>
 *
 * @author gclm
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(value = {ChaosProperties.class})
@ServletComponentScan(basePackages = "club.gclmit.chaos.logger")
@ConditionalOnProperty(prefix = "chaos.logger", value = "enabled", havingValue = "true")
public class ChaosLoggerConfig implements WebMvcConfigurer {

    @Autowired
    private ChaosProperties properties;

    /**
     * <p>
     * 配置 {@link ChaosLoggerProperties}
     * </p>
     *
     * @return club.gclmit.chaos.storage.client.StorageClient
     * @author gclm
     */
    @Bean
    public ChaosLoggerProperties chaosLoggerProperties() {
        return properties.getLogger();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer() {
        return builder -> builder.deserializerByType(String.class, new XssJacksonDeserializer());
    }
}