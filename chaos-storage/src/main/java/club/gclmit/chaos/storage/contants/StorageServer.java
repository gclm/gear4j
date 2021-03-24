package club.gclmit.chaos.storage.contants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 *  云服务商
 * </p>
 *
 * @author gclm
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StorageServer {

    /**
     *  阿里云
     */
    ALIYUN(0,"阿里云"),

    /**
     * 七牛云
     */
    QINIU(1,"七牛云"),

    /**
     *  腾讯云
     */
    TENCENT(2,"腾讯云"),

    /**
     * 又拍云
     */
    UPYUN(3,"又拍云"),

    /**
     * UCLOUD
     */
    UCLOUD(4,"UCLOUD"),

    /**
     * 华为云
     */
    HUAWEI(5,"华为云");

    /**
     *  code
     */
    private Integer code;

    /**
     * 服务
     */
    private String name;

}
