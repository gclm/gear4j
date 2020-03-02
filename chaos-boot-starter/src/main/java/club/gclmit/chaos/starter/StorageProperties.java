package club.gclmit.chaos.starter;


import club.gclmit.chaos.storage.properties.CloudStorage;
import org.hibernate.validator.constraints.Range;
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
public class StorageProperties {

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

    @Override
    public String toString() {
        return "Storage{" +
                "type=" + type +
                ", config=" + config +
                '}';
    }
}
