package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.core.helper.LoggerHelper;
import club.gclmit.chaos.core.constants.LoggerServer;
import club.gclmit.chaos.core.helper.TimeHelper;
import club.gclmit.chaos.storage.db.pojo.FileInfo;
import club.gclmit.chaos.storage.db.pojo.FileStatus;
import club.gclmit.chaos.storage.properties.CloudStorage;
import club.gclmit.chaos.storage.properties.Storage;
import club.gclmit.chaos.storage.properties.StorageServer;
import club.gclmit.chaos.storage.exception.ChaosStorageException;
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
import java.util.ArrayList;
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
     * 判断是否操作数据库
     */
    private boolean flag;

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
        super(storage);
        if(storage.getType() == StorageServer.QCLOUD) {
            cloudStorage = storage.getConfig();
            LoggerHelper.info(LoggerServer.OSS,"腾讯云配置参数:[{}]",storage);
            cosClient = build(cloudStorage.getAccessKeyId(), cloudStorage.getAccessKeySecret(), cloudStorage.getRegion());
            flag = storage.getWriteDB();
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
        writeDB(flag,null,keys);
    }

    @Override
    public void delete(String key) {
        Assert.hasLength(key,"[腾讯云OSS]删除文件的key不能为空");
        cosClient.deleteObject(cloudStorage.getBucket(),key);
        List<String> list = new ArrayList<>();
        list.add(key);
        writeDB(flag,null,list);
    }

    @Override
    public FileInfo upload(InputStream inputStream, FileInfo fileInfo) {
        Assert.notNull(inputStream,"[腾讯云OSS]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(fileInfo.getOssKey(),"[腾讯云OSS]上传文件失败，请检查上传文件的 key 是否正常");


        String key = fileInfo.getOssKey();
        String url = null;
        String eTag = null;

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
            UploadResult uploadResult = upload.waitForUploadResult();
            eTag = uploadResult.getETag();

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

        fileInfo.setUrl(url);
        fileInfo.seteTag(eTag);
        fileInfo.setUploadTime(TimeHelper.toMillis());
        fileInfo.setStatus(FileStatus.UPLOAD_SUCCESS.getId());
        // 写入数据库
        writeDB(flag,fileInfo,null);
        return fileInfo;
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
