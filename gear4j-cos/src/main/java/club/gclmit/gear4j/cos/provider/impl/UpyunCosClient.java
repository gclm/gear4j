package club.gclmit.gear4j.cos.provider.impl;

import java.io.InputStream;
import java.util.List;

import club.gclmit.gear4j.cos.domain.CosProvider;
import club.gclmit.gear4j.cos.domain.FileInfo;
import club.gclmit.gear4j.cos.provider.AbstractCosClient;
import club.gclmit.gear4j.cos.provider.CosClient;

/**
 * 又拍云
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since jdk11
 */
public class UpyunCosClient extends AbstractCosClient implements CosClient {

    public UpyunCosClient(CosProvider cosProvider) {
        super(cosProvider);
    }

    @Override
    public FileInfo upload(InputStream inputStream, FileInfo fileInfo) {
        return null;
    }

    @Override
    public void batchDelete(List<String> keys) {

    }

    @Override
    public void delete(String key) {

    }
}
