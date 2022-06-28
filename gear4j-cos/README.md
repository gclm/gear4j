# gear4j-cos

## 基本介绍

1. 采用工厂模式、构造者模式统一抽象封装各大云对象存储服务(COS)
3. 支持阿里云、华为云、腾讯云、ufile、go-fastdfs

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
compile("club.gclmit:gear4j-cos:${version}")
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

    private static void uploadFile(CloudStorage cosProvider, File file) {
        CloudStorageClient client = CloudStorageFactory.build(cosProvider);
        System.out.println("===============文件上传===============");
        System.out.println(client.upload(file));
        System.out.println("=================================");
    }

    private static void uploadByte(CloudStorage cosProvider, String content) {
        CloudStorageClient client = CloudStorageFactory.build(cosProvider);
        System.out.println("===============字节上传===============");
        String fileName = IdUtil.fastSimpleUUID() + ".txt";
        String key = IdUtil.fastSimpleUUID();
        System.out.println(client.upload(content, key, fileName));
        System.out.println("=================================");
    }

    private static void delete(CloudStorage cosProvider, String key) {
        CloudStorageClient client = CloudStorageFactory.build(cosProvider);
        System.out.println("===============文件单个删除===============");
        client.delete(key);
        ;
        System.out.println("=================================");
    }

    private static void batchDelete(CloudStorage cosProvider, List<String> keys) {
        CloudStorageClient client = CloudStorageFactory.build(cosProvider);
        System.out.println("===============文件批量删除===============");
        client.batchDelete(keys);
        System.out.println("=================================");
    }

    /**
     * 阿里云配置
     */
    @Test
    public void aliyun() {
        CloudStorageConfig cosProviderConfig = new CloudStorageConfig();
        cosProviderConfig.setAccessKeyId("access-key-id");
        cosProviderConfig.setAccessKeySecret("access-key-secret");
        cosProviderConfig.setBucket("bucket-name");
        cosProviderConfig.setRegion("endpoint");
        cosProviderConfig.setPrefix("prefix");

        CloudStorage cosProvider = new CloudStorage();
        cosProvider.setType(StorageServer.ALIYUN);
        cosProvider.setConfig(cosProviderConfig);

        uploadByte(cosProvider, "hi,gclm");
        uploadFile(cosProvider, new File(FILE_PATH));
    }

    /**
     * 腾讯云
     */
    @Test
    public void qcloud() {
        CloudStorageConfig cosProviderConfig = new CloudStorageConfig();
        cosProviderConfig.setAccessKeyId("access-key-id");
        cosProviderConfig.setAccessKeySecret("access-key-secret");
        cosProviderConfig.setBucket("bucket-name");
        cosProviderConfig.setRegion("Region");
        cosProviderConfig.setPrefix("prefix");

        CloudStorage cosProvider = new CloudStorage();
        cosProvider.setType(StorageServer.TENCENT);
        cosProvider.setConfig(cosProviderConfig);

    }

    /**
     * fastdfs
     */
    @Test
    public void fastdfs() {
        CloudStorageConfig cosProviderConfig = new CloudStorageConfig();
        cosProviderConfig.setBucket("group");
        cosProviderConfig.setEndpoint("http://127.0.0.1:9999");

        CloudStorage cosProvider = new CloudStorage();
        cosProvider.setType(StorageServer.FASTDFS);
        cosProvider.setConfig(cosProviderConfig);

        File file = new File(FILE_PATH);
        uploadFile(cosProvider, file);

        String content = "test" + IdUtil.fastSimpleUUID();
        uploadByte(cosProvider, content);

    }

}
```