package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.core.helper.file.FileHelper;
import club.gclmit.chaos.core.helper.file.MimeType;
import club.gclmit.chaos.core.helper.logger.Logger;
import club.gclmit.chaos.core.helper.logger.LoggerServer;
import club.gclmit.chaos.storage.StorageClient;
import club.gclmit.chaos.storage.exception.ChaosStorageException;
import club.gclmit.chaos.storage.properties.CloudStorage;
import club.gclmit.chaos.storage.properties.Storage;
import club.gclmit.chaos.storage.properties.StorageServer;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.DeleteError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import java.io.*;
import java.util.Collections;
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
        Assert.notNull(storage,"[Minio]配置参数不能为空");
        if(storage.getType() == StorageServer.MINIO.getValue()) {
            cloudStorage = storage.getConfig();
            Logger.debug(LoggerServer.OSS,"Minio配置参数:[{}]",storage);
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
        Assert.isTrue(file.exists(),"[Minio]上传文件不能为空");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ChaosStorageException("[Minio]上传失败，上传文件不存在");
        }
        return upload(fileInputStream,getPath(cloudStorage.getPrefix(), FileHelper.getSuffix(file)),FileHelper.getContentType(file));
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
        Assert.notEmpty(Collections.singleton(data),"[Minio]上传文件失败，请检查 byte[] 是否正常");
        Assert.hasLength(key,"[Minio]上传文件失败，请检查上传文件的 key 是否正常");
        return upload(new ByteArrayInputStream(data),key, MimeType.TXT.getMimeType());
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
    public String upload(InputStream inputStream , String key,String contentType) {
        Assert.notNull(inputStream,"[Minio]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(key,"[Minio]上传文件失败，请检查上传文件的 key 是否正常");
        String url = null;
        try {
            // 简单上传
            minioClient.putObject(cloudStorage.getBucket(), key, inputStream, Long.valueOf(inputStream.available()), null, null, contentType);
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
        return url;
    }
}

