package club.gclmit.chaos.storage.properties;

import org.hibernate.validator.constraints.Range;

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
    @Range(min = 1,max =5 ,message = "服务商类型错误")
    private int type;

    /**
     *  OSS 厂商配置
     */
    private CloudStorage config = new CloudStorage();

    /**
     * 是否保存日志到数据库中
     */
    private boolean writeDB = true;

    public int getType() {
        return type;
    }

    public void setType(int type) {
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
