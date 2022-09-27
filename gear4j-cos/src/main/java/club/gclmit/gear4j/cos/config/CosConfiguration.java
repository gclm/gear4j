package club.gclmit.gear4j.cos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import club.gclmit.gear4j.cos.domain.CosProvider;
import club.gclmit.gear4j.cos.provider.CosClient;
import club.gclmit.gear4j.cos.provider.CosClientBuilder;

/**
 * 云存储客户端配置信息封装
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/27 13:19
 * @since jdk11
 */
@Configuration
@EnableConfigurationProperties(value = {CosProvider.class})
@ConditionalOnProperty(prefix = "gear4j.cos", name = "enabled", havingValue = "true")
public class CosConfiguration {

    @Autowired
    private CosProvider cosProvider;

    @Bean
    public CosClient cosClient() {
        return CosClientBuilder.getClient(cosProvider);
    }
}
