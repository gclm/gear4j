package club.gclmit.chaos.storage.client;

import	java.util.ArrayList;

import club.gclmit.chaos.exception.ChaosStorageException;
import club.gclmit.chaos.helper.file.FileHelper;
import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import club.gclmit.chaos.storage.StorageClient;
import club.gclmit.chaos.storage.properties.CloudStorage;
import club.gclmit.chaos.storage.properties.Storage;
import club.gclmit.chaos.storage.properties.StorageServer;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * <p>
 *  腾讯云存储配置
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-24 17:04:00
 * @version: V1.0
 * @since JDK1.8
 */
public class QCloudStorageClient extends StorageClient {

    /**
     * 腾讯云 OSS客户端
     */
    private COSClient cosClient;

    /**
     * 服务器配置
     */
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
    public QCloudStorageClient(Storage storage) {
        Assert.notNull(storage,"[腾讯云OSS]配置参数不能为空");
        if(storage.getType() == StorageServer.QUCLOUD.getValue()) {
            cloudStorage = storage.getConfig();
            Logger.info(LoggerServer.OSS,"腾讯云配置参数:[{}]",storage);
            cosClient = build(cloudStorage.getAccessKeyId(), cloudStorage.getAccessKeySecret(), cloudStorage.getRegion());
        } else {
            throw new ChaosStorageException("[腾讯云OSS]上传文件失败，请检查配置参数");
        }
    }

    @Override
    public void delete(List<String> keys) {
        Assert.notEmpty(keys,"[腾讯云OSS]批量删除文件的 keys 不能为空");
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(cloudStorage.getBucket());
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();
        for (String key : keys) {
            keyList.add(new DeleteObjectsRequest.KeyVersion(key));
        }
        deleteObjectsRequest.setKeys(keyList);
        try {
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeleteObjectsResult.DeletedObject> deletedObjects = deleteObjectsResult.getDeletedObjects();
        } catch (MultiObjectDeleteException e) {
            List<MultiObjectDeleteException.DeleteError> errors = e.getErrors();
            throw new ChaosStorageException("[腾讯云OSS]删除文件失败（部分成功部分失败）" + errors.toString());
        } catch (CosServiceException e) { // 如果是其他错误，例如参数错误， 身份验证不过等会抛出 CosServiceException
            throw new ChaosStorageException("[腾讯云OSS]其他错误，例如参数错误， 身份验证不过");
        } catch (CosClientException e) { // 如果是客户端错误，例如连接不上COS
            throw new ChaosStorageException("[腾讯云OSS]客户端错误，例如连接不上COS");
        }
    }

    @Override
    public void delete(String key) {
        Assert.hasLength(key,"[腾讯云OSS]删除文件的key不能为空");
        cosClient.deleteObject(cloudStorage.getBucket(),key);
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
        Assert.isTrue(file.exists(),"[腾讯云OSS]上传文件不能为空");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ChaosStorageException("[腾讯云OSS]上传失败，上传文件不存在");
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
        Assert.notEmpty(Collections.singleton(data),"[腾讯云OSS]上传文件失败，请检查 byte[] 是否正常");
        Assert.hasLength(key,"[腾讯云OSS]上传文件失败，请检查上传文件的 key 是否正常");
        return upload(new ByteArrayInputStream(data),key);
    }

    @Override
    public String upload(InputStream inputStream, String key) {
        Assert.notNull(inputStream,"[腾讯云OSS]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(key,"[腾讯云OSS]上传文件失败，请检查上传文件的 key 是否正常");
        String url = null;
        /**
         *  创建线程池
         */
        ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(4,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

        TransferManager transferManager = new TransferManager(cosClient, threadPool);

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());

            PutObjectRequest putObjectRequest = new PutObjectRequest(cloudStorage.getBucket(),key,inputStream,objectMetadata);
            Upload upload = transferManager.upload(putObjectRequest);
            upload.waitForUploadResult();

        } catch (Exception e) {
            throw new ChaosStorageException("[腾讯云OSS]上传文件失败，请检查配置信息", e);
        } finally {
            transferManager.shutdownNow();
            cosClient.shutdown();
        }
        if (key != null) {
            // 拼接文件访问路径。由于拼接的字符串大多为String对象，而不是""的形式，所以直接用+拼接的方式没有优势
            StringBuffer path = new StringBuffer();
            path.append(cloudStorage.getProtocol()).append("://").append(cloudStorage.getBucket()).append(".cos.").append(cloudStorage.getRegion()).append(".myqcloud.com").append("/").append(key);
            if (StringUtils.isNotBlank(cloudStorage.getStyleName())) {
                path.append(cloudStorage.getStyleName());
            }
            url = path.toString();
        }
        return url;
    }


    /**
     *  构建 COSClient 客户端
     *
     * @author gclm
     * @param: secretId
     * @param: secretKey
     * @param: region
     * @date 2020/1/2 2:29 下午
     * @return: com.qcloud.cos.COSClient
     * @throws
     */
    public COSClient build(String secretId,String secretKey,String region){

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        /**
         * 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
         *  clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
         */
        Region cosRegion = new Region(region);
        ClientConfig clientConfig = new ClientConfig(cosRegion);

        /**
         * 生成 cos 客户端。
         */
        return new COSClient(cred, clientConfig);
    }

}
