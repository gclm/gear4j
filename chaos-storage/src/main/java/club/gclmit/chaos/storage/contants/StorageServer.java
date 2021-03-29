package club.gclmit.chaos.storage.contants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 云服务商
 * </p>
 *
 * @author gclm
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StorageServer {

    /**
     * 阿里云
     */
    ALIYUN(0, "ali", "阿里云"),

    /**
     * 七牛云
     */
    QINIU(1, "qiniu", "七牛云"),

    /**
     * 腾讯云
     */
    TENCENT(2, "tencent", "腾讯云"),

    /**
     * 又拍云
     */
    UPYUN(3, "upyun", "又拍云"),

    /**
     * UCLOUD
     */
    UCLOUD(4, "ucloud", "UCLOUD"),

    /**
     * 华为云
     */
    HUAWEI(5, "huawei", ",华为云"),


    /**
     * fast-dfs
     */
    FAST_DFS(6, "go-fastdfs", "华为云");

    /**
     * code
     */
    private Integer id;

    /**
     * code
     */
    private String code;

    /**
     * 服务
     */
    private String name;

}
