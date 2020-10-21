package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.lang.log.Logger;
import club.gclmit.chaos.core.lang.log.LoggerServer;
import club.gclmit.chaos.starter.properties.ChaosStorageProperties;
import club.gclmit.chaos.starter.properties.ChaosWebProperties;
import club.gclmit.chaos.storage.CloudStorageFactory;
import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.model.Storage;
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

    /**
     * <p>
     *  配置 StorageClient
     * </p>
     *
     * @author gclm
     * @return club.gclmit.chaos.storage.client.StorageClient
     */
    @Bean
    @ConditionalOnProperty(prefix = "chaos.storage",value = "enabled",havingValue = "true")
    public StorageClient storageClient (){
        Logger.debug(LoggerServer.CHAOS,"读取 properties的数据:{}",properties);
        Storage storage = new Storage();
        BeanUtils.copyProperties(properties,storage);
        Logger.debug(LoggerServer.CHAOS,"自动注入的storage:{}",storage);
        return CloudStorageFactory.build(storage);
    }
}