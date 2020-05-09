package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.starter.properties.ChaosStorageConfig;
import club.gclmit.chaos.starter.properties.ChaosWebConfig;
import club.gclmit.chaos.storage.CloudStorageFactory;
import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.properties.Storage;
import com.tuyang.beanutils.BeanCopyUtils;
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
@EnableConfigurationProperties(value = {ChaosStorageConfig.class, ChaosWebConfig.class})
public class ChaosAutoConfiguration {

    @Autowired
    private ChaosStorageConfig properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "chaos.storage",value = "enabled",havingValue = "true",matchIfMissing = true)
    public StorageClient storageClient (){
        Logger.debug(LoggerServer.CHAOS,"读取 properties的数据:{}",properties);
        Storage storage = BeanCopyUtils.copyBean(properties,Storage.class);
        Logger.debug(LoggerServer.CHAOS,"自动注入的storage:{}",storage);
        return CloudStorageFactory.build(storage);
    }
}
