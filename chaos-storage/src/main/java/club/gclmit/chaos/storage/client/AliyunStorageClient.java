package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.exception.ChaosStorageException;
import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import club.gclmit.chaos.storage.StorageClient;
import club.gclmit.chaos.storage.properties.CloudStorage;
import club.gclmit.chaos.storage.properties.Storage;
import club.gclmit.chaos.storage.properties.StorageServer;
import club.gclmit.chaos.helper.file.FileHelper;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * aliyun 服务实现
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-23 18:57:00
 * @version: V1.0
 * @since JDK1.8
 */
public class AliyunStorageClient extends StorageClient {

    /**
     * 阿里云 OSS客户端
     */
    private OSS ossClient;

    private CloudStorage cloudStorage;

    /**
     * <p>
     *  初始化配置，获取当前项目配置文件，创建初始化 ossClient 客户端
     * </p>
     *
     * @summary httpdoc 方法注解
     * @author 孤城落寞
     * @date 2019/10/23 19:12
     * @throws
     */
    public AliyunStorageClient(Storage storage) {
        Assert.notNull(storage,"[阿里云OSS]配置参数不能为空");
        if(storage.getType() == StorageServer.ALIYUN.getValue()) {
            cloudStorage = storage.getConfig();
            Logger.info(LoggerServer.OSS,"阿里云配置参数:[{}]",storage);
            // 创建OSSClient实例
            ossClient = new OSSClientBuilder().build(cloudStorage.getRegion(),cloudStorage.getAccessKeyId(),cloudStorage.getAccessKeySecret());
        } else {
            throw new ChaosStorageException("[阿里云OSS]上传文件失败，请检查 阿里云OSS 配置");
        }
    }

    /**
     * <p>
     *  批量删除多个文件
     * </p>
     *
     * @author 孤城落寞
     * @param: keys 文件路径集合
     * @date 2019/10/23 19:55
     * @throws
     */
    @Override
    public void delete(List<String> keys) {
         Assert.notEmpty(keys,"[阿里云OSS]批量删除文件的 keys 不能为空");
         ossClient.deleteObjects(new DeleteObjectsRequest(cloudStorage.getBucket()).withKeys(keys));
    }

    /**
     * <p>
     *  删除文件
     * </p>
     *
     * @author 孤城落寞
     * @param: key 文件路径
     * @date 2019/10/23 19:55
     * @throws
     */
    @Override
    public void delete(String key) {
        Assert.hasLength(key,"[阿里云OSS]删除文件的key不能为空");
        ossClient.deleteObject(cloudStorage.getBucket(),key);
    }

    /**
     * <p>
     *  上传文件
     * </p>
     *
     * @author 孤城落寞
     * @param: file 文件
     * @date 2019/10/23 19:54
     * @return: java.lang.String
     * @throws
     */
    @Override
    public String upload(File file) {
        Assert.isTrue(file.exists(),"[阿里云OSS]上传文件不能为空");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ChaosStorageException("[阿里云OSS]上传失败，上传文件不存在");
        }
        return upload(fileInputStream,getPath(cloudStorage.getPrefix(), FileHelper.getSuffix(file)));
    }

    /**
     * <p>
     *  上传字节数组
     * </p>
     *
     * @author 孤城落寞
     * @param: data 字节数组
     * @param: key  文件路径
     * @date 2019/10/23 19:52
     * @return: java.lang.String 文件路径
     * @throws
     */
    @Override
    public String upload(byte[] data, String key) {
        Assert.notEmpty(Collections.singleton(data),"[阿里云OSS]上传文件失败，请检查 byte[] 是否正常");
        Assert.hasLength(key,"[阿里云OSS]上传文件失败，请检查上传文件的 key 是否正常");
        return upload(new ByteArrayInputStream(data),key);
    }


    /**
     * <p>
     *  上传文件基础方法
     * </p>
     *
     * @author 孤城落寞
     * @param: inputStream 上传文件流
     * @param: key  文件路径
     * @date 2019/10/23 19:52
     * @return: java.lang.String 返回文件路径
     * @throws
     */
    @Override
    public String upload(InputStream inputStream , String key) {
        Assert.notNull(inputStream,"[阿里云OSS]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(key,"[阿里云OSS]上传文件失败，请检查上传文件的 key 是否正常");

        String url = null;
        try {
            // 简单上传
            ossClient.putObject(new PutObjectRequest(cloudStorage.getBucket(), key, inputStream));
        } catch (Exception e) {
            throw new ChaosStorageException("[阿里云OSS]上传文件失败，请检查配置信息", e);
        } finally {
            ossClient.shutdown();
        }

        if (key != null) {
            // 拼接文件访问路径。由于拼接的字符串大多为String对象，而不是""的形式，所以直接用+拼接的方式没有优势
            StringBuffer path = new StringBuffer();
            path.append(cloudStorage.getProtocol()).append("://").append(cloudStorage.getBucket()).append(".").append(cloudStorage.getRegion()).append("/").append(key);
            if (StringUtils.isNotBlank(cloudStorage.getStyleName())) {
                path.append(cloudStorage.getStyleName());
            }
            url = path.toString();
        }
        return url;
    }

}
