package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.storage.Storage;
import club.gclmit.chaos.storage.pojo.CloudStorage;
import club.gclmit.chaos.storage.pojo.FileInfo;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  又拍云存储配置
 * </p>
 *
 * @author gclm
 */
public class UpyunStorageClient extends StorageClient {

    /**
     *  又拍云客户端配置
     */
    private CloudStorage storage;

    public UpyunStorageClient(Storage storage) {
        super(storage);
    }

    @Override
    public FileInfo upload(InputStream inputStream, FileInfo fileInfo) {
        return null;
    }

    @Override
    public void delete(List<String> keys) {

    }

    @Override
    public void delete(String key) {

    }
}
