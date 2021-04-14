package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.utils.DateUtils;
import club.gclmit.chaos.core.utils.StringUtils;
import club.gclmit.chaos.storage.Storage;
import club.gclmit.chaos.storage.contants.FileStatus;
import club.gclmit.chaos.storage.contants.StorageServer;
import club.gclmit.chaos.storage.pojo.CloudStorage;
import club.gclmit.chaos.storage.pojo.FileInfo;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 华为云 服务实现
 * endpoint: https://developer.huaweicloud.com/endpoint?OBS
 * </p>
 *
 * @author gclm
 */
@Slf4j
public class HuaweiStorageClient extends StorageClient {

    /**
     * 华为云 Obs客户端
     */
    private ObsClient obsClient;

    private CloudStorage cloudStorage;


    /**
     * <p>
     * 初始化配置，获取当前项目配置文件，创建初始化 ossClient 客户端
     * </p>
     *
     * @param storage Storage
     * @author 孤城落寞
     */
    public HuaweiStorageClient(Storage storage) {
        super(storage);
        if (storage.getType() == StorageServer.HUAWEI) {
            cloudStorage = storage.getConfig();
            log.debug("[{}]配置参数:[{}]", StorageServer.HUAWEI.getName(), storage);
            // 创建OSSClient实例
            obsClient = new ObsClient(cloudStorage.getAccessKeyId(), cloudStorage.getAccessKeySecret(), cloudStorage.getEndpoint());
        } else {
            throw new ChaosException("[{}]上传文件失败，请检查 [{}] 配置", StorageServer.HUAWEI.getName(), StorageServer.HUAWEI.getName());
        }
    }

    /**
     * <p>
     * 批量删除多个文件
     * </p>
     *
     * @param keys 文件路径集合
     * @author 孤城落寞
     */
    @Override
    public void delete(List<String> keys) {
        Assert.notEmpty(keys, "[华为云OBS]批量删除文件的 keys 不能为空");
        for (String key : keys) {
            delete(key);
        }
    }

    /**
     * <p>
     * 删除文件
     * </p>
     *
     * @param key 文件路径
     * @author 孤城落寞
     */
    @Override
    public void delete(String key) {
        Assert.hasLength(key, "[华为云OBS]删除文件的key不能为空");
        obsClient.deleteObject(cloudStorage.getBucket(), key);
        List<String> list = new ArrayList<>();
        list.add(key);
    }

    /**
     * <p>
     * 上传文件基础方法
     * </p>
     *
     * @param inputStream 上传文件流
     * @param fileInfo    文件信息
     * @return FileInfo 文件信息
     * @author 孤城落寞
     */
    @Override
    public FileInfo upload(InputStream inputStream, FileInfo fileInfo) {
        Assert.notNull(inputStream, "[华为云OBS]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(fileInfo.getOssKey(), "[华为云OBS]上传文件失败，请检查上传文件的 key 是否正常");

        String key = fileInfo.getOssKey();

        String url = null;
        String eTag = null;
        try {
            PutObjectResult putObject = obsClient.putObject(cloudStorage.getBucket(), key, inputStream);
            eTag = putObject.getEtag();
        } catch (Exception e) {
            throw new ChaosException("[华为云OBS]上传文件失败，请检查配置信息", e);
        }

        if (key != null) {
            /**
             *  拼接文件访问路径。由于拼接的字符串大多为String对象，而不是""的形式，所以直接用+拼接的方式没有优势
             *  2020.04.09 补充，jdk8之后 + 底层采用 StringBuilder 和 + 没有什么区别，但是建议使用StringBuilder
             */
            StringBuilder path = new StringBuilder();
            path.append(cloudStorage.getProtocol()).append("://").append(cloudStorage.getBucket()).append(".").append(cloudStorage.getEndpoint()).append("/").append(key);
            if (StringUtils.isNotBlank(cloudStorage.getStyleName())) {
                path.append(cloudStorage.getStyleName());
            }
            url = path.toString();
        }

        fileInfo.setETag(eTag);
        fileInfo.setUrl(url);
        fileInfo.setUploadTime(DateUtils.getMilliTimestamp());
        fileInfo.setStatus(FileStatus.UPLOAD_SUCCESS.getId());
        return fileInfo;
    }
}
