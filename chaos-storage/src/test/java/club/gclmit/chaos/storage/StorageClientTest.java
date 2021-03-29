package club.gclmit.chaos.storage;

import club.gclmit.chaos.core.util.HttpUtils;
import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.contants.StorageServer;
import club.gclmit.chaos.storage.pojo.CloudStorage;
import cn.hutool.core.util.IdUtil;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    public static final String FILE_URL = "/home/gclm/1617005255007.jpg";
    public static final String MAC_FILE_URL = "/Users/gclm/Pictures/avatar.jpg";

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

    /**
     * fastdfs
     */
    @Test
    public void fastdfs() {
        CloudStorage cloudStorage = new CloudStorage();
        cloudStorage.setBucket("group1");
        cloudStorage.setEndpoint("http://127.0.0.1:9999");

        Storage storage = new Storage();
        storage.setType(StorageServer.FAST_DFS);
        storage.setConfig(cloudStorage);
        StorageClient client = CloudStorageFactory.build(storage);
        File file = new File(MAC_FILE_URL);

//        System.out.println("=================================");
//        System.out.println("文件上传");
//        FileInfo url = client.upload(file);
//        System.out.println(url);

//        System.out.println("=================================");
//        System.out.println("字节上传");
//        String str = "test" + IdUtil.fastSimpleUUID();
//        String fileName = IdUtil.fastSimpleUUID() + ".txt";
//        String key = "xxxx";
//        System.out.println(client.upload(str, key, fileName));

        System.out.println("=================================");
        System.out.println("文件单个删除");
        client.delete("/group1/20210329/e00809c8eb41ba42082f2d34d94cf27c.jpg");

//
//        List<String> urls = new ArrayList<>();
//
//        urls.add("662342787223519232.txt");
//        urls.add("662343182725414912.txt");
//        urls.add("image/20200102/662343179269308416.png");
//        client.delete(urls);
    }


    @Test
    public void upload() {
        String uploadUrl = "http://localhost:9999/group/upload";
        File file = new File(FILE_URL);
        String result = HttpUtils.buildHttp().async(uploadUrl)
                .addFilePara("file", file).addBodyPara("path", IdUtil.fastSimpleUUID() + ".jpg")
                .addBodyPara("scene", "default").addBodyPara("output", "json")
                .post().getResult().getBody().toString();
        System.out.println(result);
    }

}
