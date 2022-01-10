package club.gclmit.chaos.storage.service.impl;

import club.gclmit.chaos.storage.pojo.CloudStorage;
import club.gclmit.chaos.storage.pojo.FileInfo;
import club.gclmit.chaos.storage.service.AbstractStorageClient;

import java.io.InputStream;
import java.util.List;

/**
 * 又拍云
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since jdk11
 */
public class UpyunCloudStorageClient extends AbstractStorageClient {

	public UpyunCloudStorageClient(CloudStorage cloudStorage) {
		super(cloudStorage);
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
