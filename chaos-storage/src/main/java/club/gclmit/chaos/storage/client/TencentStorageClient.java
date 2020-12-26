package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.storage.contants.FileStatus;
import club.gclmit.chaos.storage.contants.StorageServer;
import club.gclmit.chaos.storage.Storage;
import club.gclmit.chaos.storage.pojo.CloudStorage;
import club.gclmit.chaos.storage.pojo.FileInfo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  腾讯云存储配置
 * </p>
 *
 * @author gclm
 */
@Slf4j
public class TencentStorageClient extends StorageClient {

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
     * @author 孤城落寞
     * @param storage Storage
     */
    public TencentStorageClient(Storage storage) {
        super(storage);
        if(storage.getType() == StorageServer.TENCENT) {
            cloudStorage = storage.getConfig();
            log.info("腾讯云配置参数:[{}]",storage);
            cosClient = build(cloudStorage.getAccessKeyId(), cloudStorage.getAccessKeySecret(), cloudStorage.getRegion());
        } else {
            throw new ChaosException("[腾讯云OSS]上传文件失败，请检查配置参数");
        }
    }

    /**
     * <p>
     *  批量删除文件
     * </p>
     *
     * @author gclm
     * @param keys 文件keys
     */
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
            cosClient.deleteObjects(deleteObjectsRequest);
        } catch (MultiObjectDeleteException e) {
            List<MultiObjectDeleteException.DeleteError> errors = e.getErrors();
            throw new ChaosException("[腾讯云OSS]删除文件失败（部分成功部分失败）" + errors.toString());
        } catch (CosServiceException e) { // 如果是其他错误，例如参数错误， 身份验证不过等会抛出 CosServiceException
            throw new ChaosException("[腾讯云OSS]其他错误，例如参数错误， 身份验证不过");
        } catch (CosClientException e) { // 如果是客户端错误，例如连接不上COS
            throw new ChaosException("[腾讯云OSS]客户端错误，例如连接不上COS");
        }
    }

    /**
     * <p>
     *  删除文件
     * </p>
     *
     * @author gclm
     * @param key 文件key
     */
    @Override
    public void delete(String key) {
        Assert.hasLength(key,"[腾讯云OSS]删除文件的key不能为空");
        cosClient.deleteObject(cloudStorage.getBucket(),key);
    }


    /**
     * <p>
     *  上传文件基础方法
     * </p>
     *
     * @author 孤城落寞
     * @param inputStream 上传文件流
     * @param fileInfo    文件信息
     * @return FileInfo 文件信息
     */
    @Override
    public FileInfo upload(InputStream inputStream, FileInfo fileInfo) {

        Assert.notNull(inputStream,"[腾讯云OSS]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(fileInfo.getOssKey(),"[腾讯云OSS]上传文件失败，请检查上传文件的 key 是否正常");


        String key = fileInfo.getOssKey();
        String url = null;
        String eTag = null;

        /**
         *  创建大小为5的线程池
         */
        //noinspection AlibabaThreadShouldSetName
        ExecutorService executorService = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));

        TransferManager transferManager = new TransferManager(cosClient, executorService);

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());

            PutObjectRequest putObjectRequest = new PutObjectRequest(cloudStorage.getBucket(),key,inputStream,objectMetadata);
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            eTag = uploadResult.getETag();

        } catch (Exception e) {
            throw new ChaosException("[腾讯云OSS]上传文件失败，请检查配置信息", e);
        } finally {
            transferManager.shutdownNow();
            cosClient.shutdown();
        }
        if (key != null) {
            // 拼接文件访问路径。由于拼接的字符串大多为String对象，而不是""的形式，所以直接用+拼接的方式没有优势
            StringBuilder path = new StringBuilder();
            path.append(cloudStorage.getProtocol()).append("://").append(cloudStorage.getBucket()).append(".cos.").append(cloudStorage.getRegion()).append(".myqcloud.com").append("/").append(key);
            if (StringUtils.isNotBlank(cloudStorage.getStyleName())) {
                path.append(cloudStorage.getStyleName());
            }
            url = path.toString();
        }

        fileInfo.setUrl(url);
        fileInfo.setETag(eTag);
        fileInfo.setUploadTime(DateUtils.getMilliTimestamp());
        fileInfo.setStatus(FileStatus.UPLOAD_SUCCESS.getId());
        return fileInfo;
    }

    /**
     *  构建 COSClient 客户端
     *
     * @author gclm
     * @param secretId  secretId
     * @param secretKey secretKey
     * @param region    region
     * @return com.qcloud.cos.COSClient
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
