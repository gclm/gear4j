package club.gclmit.chaos.storage.properties;

import java.io.Serializable;

/**
 * <p>
 *  云存储客户端封装 (支持七牛、阿里云、腾讯云、又拍云)
 * </p>
 *
 * @author gclm
 */
public class CloudStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * AccessKey ID
     */
    private String accessKeyId;

    /**
     * Access Key Secret
     */
    private String accessKeySecret;

    /**
     * 仓库/存储桶名称
     */
    private String bucket;

    /**
     * 对象存储服务的URL
     */
    private String endpoint;

    /**
     * 地域节点
     */
    private String region;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 样式名 - 需要带分隔符
     */
    private String styleName;

    /**
     * 当前网络协议
     */
    private String protocol = "https";

    public CloudStorage() {
    }

    public CloudStorage(String accessKeyId, String accessKeySecret, String bucket, String endpoint) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucket = bucket;
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "CloudStorage{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", bucket='" + bucket + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", region='" + region + '\'' +
                ", prefix='" + prefix + '\'' +
                ", styleName='" + styleName + '\'' +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
