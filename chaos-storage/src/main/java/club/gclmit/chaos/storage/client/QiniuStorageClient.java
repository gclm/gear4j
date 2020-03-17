package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.storage.db.pojo.FileInfo;
import club.gclmit.chaos.storage.properties.Storage;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  七牛云存储配置
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-24 17:04:00
 * @version: V1.0
 * @since JDK1.8
 */
public class QiniuStorageClient extends StorageClient {

    public QiniuStorageClient(Storage storage) {
        super(storage);
    }

    @Override
    public void delete(List<String> keys) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public FileInfo upload(InputStream inputStream, FileInfo fileInfo) {
        return null;
    }
}
