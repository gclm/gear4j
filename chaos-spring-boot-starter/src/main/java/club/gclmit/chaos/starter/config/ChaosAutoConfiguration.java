package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.starter.properties.ChaosStorageProperties;
import club.gclmit.chaos.starter.properties.ChaosWebProperties;
import club.gclmit.chaos.storage.CloudStorageFactory;
import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.model.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
@Configuration
@EnableConfigurationProperties(value = {ChaosStorageProperties.class, ChaosWebProperties.class})
public class ChaosAutoConfiguration {

    @Autowired
    private ChaosStorageProperties properties;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>
     * 配置 StorageClient
     * </p>
     *
     * @return club.gclmit.chaos.storage.client.StorageClient
     * @author gclm
     */
    @Bean
    @ConditionalOnProperty(prefix = "chaos.storage", value = "enabled", havingValue = "true")
    public StorageClient storageClient() {
        log.debug("读取 properties的数据:{}", StringUtils.toString(properties));
        Storage storage = new Storage();
        BeanUtils.copyProperties(properties, storage);
        log.debug("自动注入的storage:{}", StringUtils.toString(storage));
        return CloudStorageFactory.build(storage);
    }
}