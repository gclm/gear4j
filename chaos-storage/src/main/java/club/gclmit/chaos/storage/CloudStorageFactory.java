package club.gclmit.chaos.storage;

import club.gclmit.chaos.storage.client.*;
import club.gclmit.chaos.storage.properties.Storage;

/**
 * <p>
 *  oss 客户端构造器
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-24 16:53:00
 * @version: V1.0
 * @since JDK1.8
 */
public class CloudStorageFactory {

    /**
     * <p>
     *  根据枚举类型返回相应的云存储对象
     * </p>
     *
     * @summary httpdoc 方法注解
     * @author 孤城落寞
     * @param: storageClient
     * @date 2019/10/24 17:10
     * @return: club.gclmit.chaos.service.CloudStorageService
     * @throws
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
            case QCLOUD:
                client = new QCloudStorageClient(storage);
                break;
        }
        return client;
    }
}
