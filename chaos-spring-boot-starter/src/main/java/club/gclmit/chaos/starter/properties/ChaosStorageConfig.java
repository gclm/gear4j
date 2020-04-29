package club.gclmit.chaos.starter.properties;

import club.gclmit.chaos.storage.properties.Storage;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *  storage 配置
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/2 2:07 下午
 * @version: V1.0
 * @since 1.8
 */
@ConfigurationProperties("chaos.storage")
public class ChaosStorageConfig extends Storage {

    /**
     * 是否保存日志到数据库中
     */
    private boolean writeDB = true;

    public boolean isWriteDB() {
        return writeDB;
    }

    public void setWriteDB(boolean writeDB) {
        this.writeDB = writeDB;
    }
}
