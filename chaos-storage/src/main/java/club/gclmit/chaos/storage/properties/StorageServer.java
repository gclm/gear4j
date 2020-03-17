package club.gclmit.chaos.storage.properties;

/**
 * <p>
 *  云服务商
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-24 16:50:00
 * @version: V1.0
 * @since JDK1.8
 */
public enum StorageServer {

    /**
     *  阿里云
     */
    ALIYUN(1),
    /**
     * 七牛云
     */
    QINIU(2),
    /**
     *  腾讯云
     */
    QCLOUD(3),
    /**
     * 又拍云
     */
    UPYUN(4),

    /**
     * minio
     */
    MINIO(5);

    private int value;

    StorageServer(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
