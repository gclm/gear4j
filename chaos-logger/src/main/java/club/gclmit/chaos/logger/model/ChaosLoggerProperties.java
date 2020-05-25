package club.gclmit.chaos.logger.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *  日志配置工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/20 11:43 上午
 * @version: V1.0
 * @since 1.8
 */
@ConfigurationProperties("chaos.logger")
public class ChaosLoggerProperties {

    /**
     * 需要记录日志的前缀
     */
    private String prefix = "/api";

    /**
     * 需要忽略的url
     */
    private String[] ignoreUrls = {};

    /**
     * 是否保存日志到数据库中
     */
    private boolean saveLogger = true;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String[] getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(String[] ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    public boolean getSaveLogger() {
        return saveLogger;
    }

    public void setSaveLogger(boolean saveLogger) {
        this.saveLogger = saveLogger;
    }
}
