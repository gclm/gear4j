package club.gclmit.chaos.storage;

import club.gclmit.chaos.storage.client.*;
import club.gclmit.chaos.storage.model.Storage;

/**
 * <p>
 *  oss 客户端构造器
 * </p>
 *
 * @author gclm
 */
public class CloudStorageFactory {

    /**
     * <p>
     *  根据枚举类型返回相应的云存储对象
     * </p>
     *
     * @author 孤城落寞
     * @param storage Storage
     * @return club.gclmit.chaos.service.CloudStorageService
     */
    public static StorageClient build(Storage storage) {
        StorageClient client = null;
        switch (storage.getType()){
            case QINIU:
                client = new QiniuStorageClient(storage);
                break;
            case UFILE:
                client = new UfileStorageClient(storage);
                break;
            case ALIYUN:
                client = new AliyunStorageClient(storage);
                break;
            case UPYUN:
                client = new UpyunStorageClient(storage);
                break;
            case TENCENT:
                client = new TencentStorageClient(storage);
                break;
            default:
                client = new AliyunStorageClient(storage);
                break;
        }
        return client;
    }
}
