package club.gclmit.chaos.storage.properties;

import java.io.Serializable;

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
public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     * 1：阿里云    2：七牛
     * 3：腾讯云    4：又拍云
     * 5：MinIO
     */
    private StorageServer type = StorageServer.ALIYUN;

    /**
     *  OSS 厂商配置
     */
    private CloudStorage config = new CloudStorage();

    /**
     * 是否保存日志到数据库中
     */
    private boolean writeDB = true;


    public StorageServer getType() {
        return type;
    }

    public void setType(StorageServer type) {
        this.type = type;
    }

    public CloudStorage getConfig() {
        return config;
    }

    public void setConfig(CloudStorage config) {
        this.config = config;
    }

    public boolean getWriteDB() {
        return writeDB;
    }

    public void setWriteDB(boolean writeDB) {
        this.writeDB = writeDB;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "type=" + type +
                ", config=" + config +
                ", writeDB=" + writeDB +
                '}';
    }
}
