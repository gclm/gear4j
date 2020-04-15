package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.storage.properties.*;
import club.gclmit.chaos.storage.exception.ChaosStorageException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.util.Assert;
import java.io.*;
import java.util.ArrayList;
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
     * 阿里云域名
     */
    private static final String ALIYUN_DOMAIN_SUFFIX = ".aliyuncs.com";

    /**
     * 阿里云 OSS客户端
     */
    private OSS ossClient;

    private CloudStorage cloudStorage;

    /**
     * 阿里云节点地址
     */
    private String endpoint;

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
        super(storage);
        if(storage.getType() == StorageServer.ALIYUN) {
            cloudStorage = storage.getConfig();
            endpoint = cloudStorage.getRegion() + ALIYUN_DOMAIN_SUFFIX;
            Logger.debug(LoggerServer.CHAOS_STORAGE,"阿里云配置参数:[{}]",storage);
            // 创建OSSClient实例
            ossClient = new OSSClientBuilder().build(endpoint,cloudStorage.getAccessKeyId(),cloudStorage.getAccessKeySecret());
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
        List<String> list = new ArrayList<>();
        list.add(key);
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
    public FileInfo upload(InputStream inputStream, FileInfo fileInfo) {
        Assert.notNull(inputStream,"[阿里云OSS]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(fileInfo.getOssKey(),"[阿里云OSS]上传文件失败，请检查上传文件的 key 是否正常");

        String key = fileInfo.getOssKey();

        String url = null;
        String eTag = null;
        try {
            // 简单上传
            PutObjectResult putObject = ossClient.putObject(new PutObjectRequest(cloudStorage.getBucket(), key, inputStream));
            eTag = putObject.getETag();
        } catch (Exception e) {
            throw new ChaosStorageException("[阿里云OSS]上传文件失败，请检查配置信息", e);
        }

        if (key != null) {
            /**
             *  拼接文件访问路径。由于拼接的字符串大多为String对象，而不是""的形式，所以直接用+拼接的方式没有优势
             *  2020.04.09 补充，jdk8之后 + 底层采用 StringBuilder 和 + 没有什么区别，但是建议使用StringBuilder
             */
            StringBuilder path = new StringBuilder();
            if (StringUtils.isNotBlank(cloudStorage.getEndpoint())){
                endpoint = cloudStorage.getEndpoint();
            } else {
                endpoint = new StringBuilder(cloudStorage.getBucket()).append(".").append(endpoint).toString();
            }
            path.append(cloudStorage.getProtocol()).append("://").append(endpoint).append("/").append(key);
            if (StringUtils.isNotBlank(cloudStorage.getStyleName())) {
                path.append(cloudStorage.getStyleName());
            }
            url = path.toString();
        }

        fileInfo.seteTag(eTag);
        fileInfo.setUrl(url);
        fileInfo.setUploadTime(DateUtils.getMilliTimestamp());
        fileInfo.setStatus(FileStatus.UPLOAD_SUCCESS.getId());
        return fileInfo;
    }
}
