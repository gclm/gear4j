package club.gclmit.chaos.storage.properties;

/**
 * <p>
 *  云服务商
 * </p>
 *
 * @author gclm
 */
public enum StorageServer {

    /**
     *  阿里云
     */
    ALIYUN(0),
    /**
     * 七牛云
     */
    QINIU(1),

    /**
     *  腾讯云
     */
    TENCENT(2),

    /**
     * 又拍云
     */
    UPYUN(3),

    /**
     * UFile
     */
    UFILE(4);

    private Integer id;

    public Integer getId() {
        return id;
    }

    StorageServer(Integer id) {
        this.id = id;
    }
}
