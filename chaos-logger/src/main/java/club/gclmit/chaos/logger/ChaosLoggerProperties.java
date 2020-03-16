package club.gclmit.chaos.logger;

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
     * 是否保存日志到数据库中
     */
    private boolean writeDB = true;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isWriteDB() {
        return writeDB;
    }

    public void setWriteDB(boolean writeDB) {
        this.writeDB = writeDB;
    }
}
