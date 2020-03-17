package club.gclmit.chaos.starter;

import club.gclmit.chaos.core.constants.LoggerServer;
import club.gclmit.chaos.core.helper.LoggerHelper;
import club.gclmit.chaos.storage.CloudStorageFactory;
import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.properties.Storage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * chaos-storage 自动配置
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/3 5:53 下午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class ChaosAutoConfiguration {

    @Autowired
    private StorageProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "chaos.storage",value = "enabled",havingValue = "true")
    public StorageClient storageClient (){
        LoggerHelper.debug(LoggerServer.OSS,"读取 properties的数据:{}",properties);
        Storage storage = new Storage();
        BeanUtils.copyProperties(properties,storage);
        LoggerHelper.debug(LoggerServer.OSS,"自动注入的storage:{}",storage);
        return CloudStorageFactory.build(storage);
    }
}
