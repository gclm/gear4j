package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.starter.properties.ChaosProperties;
import club.gclmit.chaos.storage.CloudStorageFactory;
import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@ConditionalOnProperty(prefix = "chaos.storage", value = "enabled", havingValue = "true")
public class ChaosStorageConfig {

    @Autowired
    private ChaosProperties properties;

    /**
     * <p>
     * 配置 StorageClient
     * </p>
     *
     * @return club.gclmit.chaos.storage.client.StorageClient
     * @author gclm
     */
    @Bean
    public StorageClient storageClient() {
        Storage storage = properties.getStorage();
        log.debug("读取 properties的数据:{}", StringUtils.toString(storage));
        return CloudStorageFactory.build(storage);
    }
}