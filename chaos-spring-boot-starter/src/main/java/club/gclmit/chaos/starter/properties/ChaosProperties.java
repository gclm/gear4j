package club.gclmit.chaos.starter.properties;

import club.gclmit.chaos.logger.model.ChaosLoggerProperties;
import club.gclmit.chaos.storage.Storage;
import club.gclmit.chaos.waf.properties.ChaosWafProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Chaos Properties
 *
 * @author gclm
 */
@Data
@ConfigurationProperties("chaos")
public class ChaosProperties {

    /**
     * 存储自动注入
     */
    private Storage storage;

    /**
     * 日志管理
     */
    private ChaosLoggerProperties logger;

    /**
     * waf自动注入
     */
    private ChaosWafProperties waf;

}
