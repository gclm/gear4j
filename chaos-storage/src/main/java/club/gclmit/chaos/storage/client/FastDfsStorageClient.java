package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.io.FileUtils;
import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.HttpUtils;
import club.gclmit.chaos.storage.Storage;
import club.gclmit.chaos.storage.contants.FileStatus;
import club.gclmit.chaos.storage.contants.StorageServer;
import club.gclmit.chaos.storage.pojo.CloudStorage;
import club.gclmit.chaos.storage.pojo.FileInfo;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * go-fastdfs 服务实现
 *
 * </p>
 *
 * @author gclm
 */
@Slf4j
public class FastDfsStorageClient extends StorageClient {

    private CloudStorage cloudStorage;

    private String serverUrl;

    /**
     * <p>
     * 初始化配置，获取当前项目配置文件，创建初始化 ossClient 客户端
     * </p>
     *
     * @param storage Storage
     * @author 孤城落寞
     */
    public FastDfsStorageClient(Storage storage) {
        super(storage);
        if (storage.getType() == StorageServer.FAST_DFS) {
            log.debug("[{}]配置参数:[{}]", StorageServer.FAST_DFS.getName(), storage);
            cloudStorage = storage.getConfig();
            serverUrl = cloudStorage.getEndpoint() + "/" + cloudStorage.getBucket() + "/";
        } else {
            throw new ChaosException("[{}]上传文件失败，请检查 [{}] 配置", StorageServer.FAST_DFS.getName(), StorageServer.FAST_DFS.getName());
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
        Assert.notEmpty(keys, "[FastDFS]批量删除文件的 keys 不能为空");
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
        Assert.hasLength(key, "[FastDFS]删除文件的key不能为空");
        String url = serverUrl + "delete?path=" + key;
        String result = HttpUtils.buildHttp().async(url).get().getResult().getBody().toString();
        log.info("当前删除状态:[{}]", result);
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
        Assert.notNull(inputStream, "[FastDFS]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(fileInfo.getOssKey(), "[FastDFS]上传文件失败，请检查上传文件的 key 是否正常");

        LocalDate localDate = LocalDate.now();
        String dateFormat = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String url = null;
        try {
            File tempFile = new File(FileUtils.getRootPath(), fileInfo.getName());
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            IOUtils.copy(inputStream, outputStream);
            String uploadUrl = serverUrl + "upload";
            HttpResult result = HttpUtils.buildHttp().async(uploadUrl)
                    .addFilePara("file", tempFile)
                    .addBodyPara("path", dateFormat)
                    .addBodyPara("scene", "default").addBodyPara("output", "json2")
                    .post().getResult();

            if (result.isSuccessful()) {
                JSONObject mapper = JSONObject.parseObject(result.getBody().toString()).getJSONObject("data");
                url = mapper.getString("domain") + mapper.getString("path");
                FileUtils.del(tempFile);
                fileInfo.setOssKey(mapper.getString("path"));
            }
        } catch (Exception e) {
            throw new ChaosException("[FastDFS]上传文件失败，请检查配置信息", e);
        }

        fileInfo.setUrl(url);
        fileInfo.setUploadTime(DateUtils.getMilliTimestamp());
        fileInfo.setStatus(FileStatus.UPLOAD_SUCCESS.getId());
        return fileInfo;
    }
}
