package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.storage.Storage;
import club.gclmit.chaos.storage.pojo.FileInfo;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  七牛云存储配置
 * </p>
 *
 * @author gclm
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
