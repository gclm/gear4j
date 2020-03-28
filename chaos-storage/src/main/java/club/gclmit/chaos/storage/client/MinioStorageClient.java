package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.core.constants.LoggerServer;
import club.gclmit.chaos.core.helper.LoggerHelper;
import club.gclmit.chaos.core.helper.TimeHelper;
import club.gclmit.chaos.storage.properties.*;
import club.gclmit.chaos.storage.exception.ChaosStorageException;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.DeleteError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Minio
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/2 9:15 上午
 * @version: V1.0
 * @since 1.8
 */
public class MinioStorageClient  extends StorageClient {

    /**
     * Minio OSS客户端
     */
    private MinioClient minioClient;

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
    public MinioStorageClient(Storage storage) {
        super(storage);

        if(storage.getType() == StorageServer.MINIO) {
            cloudStorage = storage.getConfig();
            LoggerHelper.debug(LoggerServer.OSS,"Minio配置参数:[{}]",storage);
            // 创建minioClient实例
            try {
                minioClient = new MinioClient(cloudStorage.getEndpoint(), cloudStorage.getAccessKeyId(),cloudStorage.getAccessKeySecret());
            } catch (Exception e) {
                throw new ChaosStorageException("[Minio]上传文件失败，请检查 Minio 配置",e);
            }
        } else {
            throw new ChaosStorageException("[Minio]上传文件失败，请检查 Minio 配置");
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
        Assert.notEmpty(keys,"[Minio]批量删除文件的 keys 不能为空");
        StringBuilder message = new StringBuilder();
        try {
            for (Result<DeleteError> errorResult: minioClient.removeObjects(cloudStorage.getBucket(),keys)) {
                DeleteError error = errorResult.get();
                message.append("Failed to remove ").append(error.objectName()).append(" Error:").append(error.message()).append("\n");
            }
        } catch (Exception e) {
            throw new ChaosStorageException("[Minio] 批量删除文件失败,原因："+ message,e);
        }
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
        Assert.hasLength(key,"[Minio]删除文件的key不能为空");
        try {
            minioClient.removeObject(cloudStorage.getBucket(),key);
        } catch (Exception e) {
            throw new ChaosStorageException("[Minio] 删除文件失败",e);
        }
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
        Assert.notNull(inputStream,"[Minio]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(fileInfo.getOssKey(),"[Minio]上传文件失败，请检查上传文件的 key 是否正常");

        String key = fileInfo.getOssKey();
        String url = null;

        try {
            // 简单上传
            minioClient.putObject(cloudStorage.getBucket(), key, inputStream, Long.valueOf(inputStream.available()), null, null, fileInfo.getContentType());
        } catch (Exception e) {
            throw new ChaosStorageException("[Minio]上传文件失败，请检查配置信息", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (key != null) {
            // 拼接文件访问路径。由于拼接的字符串大多为String对象，而不是""的形式，所以直接用+拼接的方式没有优势
            StringBuffer path = new StringBuffer();
            path.append(cloudStorage.getEndpoint()).append("/").append(cloudStorage.getBucket()).append("/").append(key);
            if (StringUtils.isNotBlank(cloudStorage.getStyleName())) {
                path.append(cloudStorage.getStyleName());
            }
            url = path.toString();
        }

        fileInfo.setUrl(url);
        fileInfo.setUploadTime(TimeHelper.getMilliTimestamp());
        fileInfo.setStatus(FileStatus.UPLOAD_SUCCESS.getId());
        return fileInfo;
    }
}

