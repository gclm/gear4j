package club.gclmit.chaos.storage;

import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.contants.StorageServer;
import club.gclmit.chaos.storage.pojo.CloudStorage;
import cn.hutool.core.util.IdUtil;
import com.ejlchina.okhttps.OkHttps;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 测试上传的工具类
 * </p>
 *
 * @author gclm
 * @date 2020/1/2 4:40 下午
 * @since 1.8
 */
public class StorageClientTest {

    public static final String FILE_PATH = "/home/gclm/1617005255007.jpg";
    public static final String MAC_FILE_PATH = "/Users/gclm/Pictures/avatar.jpg";


    /**
     * 阿里云配置
     */
    @Test
    public void aliyun() {

        CloudStorage cloudStorage = new CloudStorage();
        cloudStorage.setAccessKeyId("access-key-id");
        cloudStorage.setAccessKeySecret("access-key-secret");
        cloudStorage.setBucket("bucket-name");
        cloudStorage.setRegion("end-point");
        cloudStorage.setPrefix("prefix");

        Storage storage = new Storage();
        storage.setType(StorageServer.ALIYUN);
        storage.setConfig(cloudStorage);
        StorageClient client = CloudStorageFactory.build(storage);
        File file = new File(FILE_PATH);
        
    }

    /**
     * 腾讯云
     */
    @Test
    public void qcloud() {
        CloudStorage cloudStorage = new CloudStorage();
        cloudStorage.setAccessKeyId("access-key-id");
        cloudStorage.setAccessKeySecret("access-key-secret");
        cloudStorage.setBucket("bucket-name");
        cloudStorage.setRegion("Region");
        cloudStorage.setPrefix("prefix");

        Storage storage = new Storage();
        storage.setType(StorageServer.TENCENT);
        storage.setConfig(cloudStorage);

    }

    /**
     * fastdfs
     */
    @Test
    public void fastDfs_upload() {
        CloudStorage cloudStorage = new CloudStorage();
        cloudStorage.setBucket("group");
        cloudStorage.setEndpoint("http://127.0.0.1:9999");

        Storage storage = new Storage();
        storage.setType(StorageServer.FAST_DFS);
        storage.setConfig(cloudStorage);

        File file = new File(FILE_PATH);
        uploadFile(storage, file);

        String content = "test" + IdUtil.fastSimpleUUID();
        uploadByte(storage, content);

    }

    @Test
    public void upload() {
        String uploadUrl = "http://localhost:9999/group/upload";
        File file = new File(FILE_PATH);
        Map<String,Object> params = new HashMap<>();
        params.put("path", IdUtil.fastSimpleUUID() + ".jpg");
        params.put("scene", "default");
        params.put("output", "json");
        String result = OkHttps.async(uploadUrl)
                .addFilePara("file", file)
                .addBodyPara(params)
                .bodyType("form")
                .post().getResult().getBody().toString();
        System.out.println(result);
    }

    private static void uploadFile(Storage storage, File file) {
        StorageClient client = CloudStorageFactory.build(storage);
        System.out.println("===============文件上传===============");
        System.out.println(client.upload(file));
        System.out.println("=================================");
    }

    private static void uploadByte(Storage storage, String content) {
        StorageClient client = CloudStorageFactory.build(storage);
        System.out.println("===============字节上传===============");
        String fileName = IdUtil.fastSimpleUUID() + ".txt";
        String key = IdUtil.fastSimpleUUID();
        System.out.println(client.upload(content, key, fileName));
        System.out.println("=================================");
    }

    private static void delete(Storage storage, String key) {
        StorageClient client = CloudStorageFactory.build(storage);
        System.out.println("===============文件单个删除===============");
        client.delete(key);
        ;
        System.out.println("=================================");
    }

    private static void batchDelete(Storage storage, List<String> keys) {
        StorageClient client = CloudStorageFactory.build(storage);
        System.out.println("===============文件批量删除===============");
        client.delete(keys);
        ;
        System.out.println("=================================");
    }

}
