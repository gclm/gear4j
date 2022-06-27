package club.gclmit.gear4j.storage.model.properties;

import club.gclmit.gear4j.storage.model.contants.ResponseDataType;
import club.gclmit.gear4j.storage.model.contants.StorageServer;
import club.gclmit.gear4j.storage.model.pojo.CloudStorage;

/**
 * 云服务商存储参数
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/27 14:13
 * @since jdk11
 */
public class ProviderProperties {

    /**
     * 类型 1：阿里云 2：七牛 3：腾讯云 4：又拍云 5：UCLOUD
     */
    private StorageServer type = StorageServer.ALIYUN;

    /**
     * OSS 厂商配置
     */
    private CloudStorage config = new CloudStorage();

    /**
     * 数据类型
     */
    private ResponseDataType result = ResponseDataType.DETAIL;

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

    public ResponseDataType getResult() {
        return result;
    }

    public void setResult(ResponseDataType result) {
        this.result = result;
    }
}
