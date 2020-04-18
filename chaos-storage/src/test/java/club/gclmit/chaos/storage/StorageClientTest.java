package club.gclmit.chaos.storage;

import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.properties.CloudStorage;
import club.gclmit.chaos.storage.properties.Storage;
import club.gclmit.chaos.storage.properties.StorageServer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  测试上传的工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/2 4:40 下午
 * @version: V1.0
 * @since 1.8
 */
public class StorageClientTest {

    public static final  String FILE_URL = "/Users/gclm/Downloads/index.png";

    /**
     * 阿里云配置
     */
    public static  void aliyun() {

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
        File file = new File(FILE_URL);

//        System.out.println("=================================");
//        System.out.println("文件上传");
//        String url = client.upload(file);
//        System.out.println(url);

//        System.out.println("=================================");
//        System.out.println("字节上传");
//        String str = "test";
//        String upload = client.upload(str.getBytes(), IDHelper.getStringId()+".txt");
//        System.out.println(upload);

        System.out.println("=================================");
        System.out.println("文件单个删除");
        client.delete("image/20200102/662339305615654912.png");

//        client.delete("xxxx.txt");
//        client.delete("xxxx.txt");

        List<String> urls = new ArrayList<>();

        urls.add("662341317505843200.txt");
        urls.add("xxxx.txt");

        client.delete(urls);

    }

    /**
     * 腾讯云
     */
    public static void qcloud(){
        CloudStorage cloudStorage = new CloudStorage();
        cloudStorage.setAccessKeyId("access-key-id");
        cloudStorage.setAccessKeySecret("access-key-secret");
        cloudStorage.setBucket("bucket-name");
        cloudStorage.setRegion("Region");
        cloudStorage.setPrefix("prefix");

        Storage storage = new Storage();
        storage.setType(StorageServer.QCLOUD);
        storage.setConfig(cloudStorage);
        StorageClient client = CloudStorageFactory.build(storage);
        File file = new File(FILE_URL);

//        System.out.println("=================================");
//        System.out.println("文件上传");
//        String url = client.upload(file);
//        System.out.println(url);
//
//        System.out.println("=================================");
//        System.out.println("字节上传");
//        String str = "test" + IDHelper.getStringId();
//        String upload = client.upload(str.getBytes(), IDHelper.getStringId()+".txt");
//        System.out.println(upload);

        System.out.println("=================================");
        System.out.println("文件单个删除");
        client.delete("image/20200102/662342784224591872.png");


        List<String> urls = new ArrayList<>();

        urls.add("662342787223519232.txt");
        urls.add("662343182725414912.txt");
        urls.add("image/20200102/662343179269308416.png");
        client.delete(urls);
    }

    public static void main(String[] args) {
//        aliyun();
        qcloud();
//          qcloud();

    }
}
