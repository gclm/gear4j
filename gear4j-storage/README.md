# gear4j-storage

## 基本介绍

1. 采用工厂模式，封装常见的外部存储
2. 支持阿里云、华为云、腾讯云、ufile、go-fastdfs

## 使用方式

### 添加依赖

#### maven

```xml

<dependency>
    <groupId>club.gclmit</groupId>
    <artifactId>gear4j-storage</artifactId>
    <version>${version}</version>
</dependency>
```

#### gradle

```groovy
compile("club.gclmit:gear4j-storage:${version}")
```

### 相关代码

```java
import StorageServer;
import CloudStorage;
import CloudStorageConfig;
import CloudStorageClient;
import cn.hutool.core.util.IdUtil;

import java.io.File;
import java.util.List;

/**
 * 测试上传的工具类
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since jdk11
 */
public class StorageClientTest {

	public static final String FILE_PATH = "/Users/gclm/Pictures/064A9498.jpg";

	private static void uploadFile(CloudStorage cloudStorage, File file) {
		CloudStorageClient client = CloudStorageFactory.build(cloudStorage);
		System.out.println("===============文件上传===============");
		System.out.println(client.upload(file));
		System.out.println("=================================");
	}

	private static void uploadByte(CloudStorage cloudStorage, String content) {
		CloudStorageClient client = CloudStorageFactory.build(cloudStorage);
		System.out.println("===============字节上传===============");
		String fileName = IdUtil.fastSimpleUUID() + ".txt";
		String key = IdUtil.fastSimpleUUID();
		System.out.println(client.upload(content, key, fileName));
		System.out.println("=================================");
	}

	private static void delete(CloudStorage cloudStorage, String key) {
		CloudStorageClient client = CloudStorageFactory.build(cloudStorage);
		System.out.println("===============文件单个删除===============");
		client.delete(key);
		;
		System.out.println("=================================");
	}

	private static void batchDelete(CloudStorage cloudStorage, List<String> keys) {
		CloudStorageClient client = CloudStorageFactory.build(cloudStorage);
		System.out.println("===============文件批量删除===============");
		client.batchDelete(keys);
		System.out.println("=================================");
	}

	/**
	 * 阿里云配置
	 */
    @Test
	public void aliyun() {
		CloudStorageConfig cloudStorageConfig = new CloudStorageConfig();
		cloudStorageConfig.setAccessKeyId("access-key-id");
		cloudStorageConfig.setAccessKeySecret("access-key-secret");
		cloudStorageConfig.setBucket("bucket-name");
		cloudStorageConfig.setRegion("endpoint");
		cloudStorageConfig.setPrefix("prefix");

		CloudStorage cloudStorage = new CloudStorage();
		cloudStorage.setType(StorageServer.ALIYUN);
		cloudStorage.setConfig(cloudStorageConfig);

		uploadByte(cloudStorage, "hi,gclm");
		uploadFile(cloudStorage, new File(FILE_PATH));
	}

	/**
	 * 腾讯云
	 */
    @Test
	public void qcloud() {
		CloudStorageConfig cloudStorageConfig = new CloudStorageConfig();
		cloudStorageConfig.setAccessKeyId("access-key-id");
		cloudStorageConfig.setAccessKeySecret("access-key-secret");
		cloudStorageConfig.setBucket("bucket-name");
		cloudStorageConfig.setRegion("Region");
		cloudStorageConfig.setPrefix("prefix");

		CloudStorage cloudStorage = new CloudStorage();
		cloudStorage.setType(StorageServer.TENCENT);
		cloudStorage.setConfig(cloudStorageConfig);

	}

	/**
	 * fastdfs
	 */
    @Test
	public void fastdfs() {
		CloudStorageConfig cloudStorageConfig = new CloudStorageConfig();
		cloudStorageConfig.setBucket("group");
		cloudStorageConfig.setEndpoint("http://127.0.0.1:9999");

		CloudStorage cloudStorage = new CloudStorage();
		cloudStorage.setType(StorageServer.FASTDFS);
		cloudStorage.setConfig(cloudStorageConfig);

		File file = new File(FILE_PATH);
		uploadFile(cloudStorage, file);

		String content = "test" + IdUtil.fastSimpleUUID();
		uploadByte(cloudStorage, content);

	}

}
```