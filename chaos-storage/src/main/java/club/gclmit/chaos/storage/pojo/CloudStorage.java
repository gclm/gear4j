package club.gclmit.chaos.storage.pojo;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *  云存储客户端封装 (支持七牛、阿里云、腾讯云、又拍云)
 * </p>
 *
 * @author gclm
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
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

    public CloudStorage(String accessKeyId, String accessKeySecret, String bucket, String endpoint) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucket = bucket;
        this.endpoint = endpoint;
    }
}
