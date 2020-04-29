package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.storage.properties.*;
import club.gclmit.chaos.storage.exception.ChaosStorageException;
import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectApiBuilder;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.HttpClient;
import cn.ucloud.ufile.util.StorageType;
import org.springframework.util.Assert;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>
 * Ufile 服务实现
 * </p>
 *
 * @author: gclm
 * @date: 2020-03-19 18:57:00
 * @version: V1.0
 * @since JDK1.8
 */
public class UfileStorageClient extends StorageClient {

    private final static String END_POINT = "ufileos.com";

    /**
     * 客户端
     */
    private ObjectApiBuilder ossClient;

    /**
     * 配置参数 
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
    public UfileStorageClient(Storage storage) {
        super(storage);
        Logger.debug(LoggerServer.CHAOS,"[Ufile]配置参数:[{}]",storage);
        if(storage.getType() == StorageServer.UFILE) {
            cloudStorage = storage.getConfig();
            if (StringUtils.isBlank(cloudStorage.getEndpoint())){
                cloudStorage.setEndpoint(END_POINT);
            }
            ossClient = build(cloudStorage.getAccessKeyId(),cloudStorage.getAccessKeySecret(),cloudStorage.getRegion(),cloudStorage.getEndpoint());
        } else {
            throw new ChaosStorageException("[Ufile]上传文件失败，请检查 阿里云OSS 配置");
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
         Assert.notEmpty(keys,"[Ufile]批量删除文件的 keys 不能为空");
         keys.forEach(key -> delete(key));
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
        Assert.hasLength(key,"[Ufile]删除文件的key不能为空");
        try {
            ossClient.deleteObject(key,cloudStorage.getBucket()).execute();
        } catch (UfileClientException e) {
            throw new ChaosStorageException("删除失败,Ufile客户端发生异常",e);
        } catch (UfileServerException e) {
            throw new ChaosStorageException("删除失败,Ufile服务器发生异常",e);
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
     * @param: fileInfo    文件对象
     * @date 2019/10/23 19:52
     * @return: java.lang.String 返回文件路径
     * @throws
     */
    @Override
    public FileInfo upload(InputStream inputStream, FileInfo fileInfo) {
        Assert.notNull(inputStream,"[Ufile]上传文件失败，请检查 inputStream 是否正常");
        Assert.hasLength(fileInfo.getOssKey(),"[Ufile]上传文件失败，请检查上传文件的 key 是否正常");

        String key = fileInfo.getOssKey();

        String url = null;
        String eTag = null;

        try {
            PutObjectResultBean response = ossClient.putObject(inputStream, fileInfo.getContentType())
                    .nameAs(fileInfo.getOssKey())
                    .toBucket(cloudStorage.getBucket())
                    /**
                     * 配置文件存储类型，分别是标准、低频、冷存，对应有效值：STANDARD | IA | ARCHIVE
                     */
                    .withStorageType(StorageType.STANDARD)
                    .execute();
            eTag = response.geteTag();
        } catch (UfileClientException e) {
            throw new ChaosStorageException("上传失败,Ufile客户端发生异常",e);
        } catch (UfileServerException e) {
            throw new ChaosStorageException("上传失败,Ufile服务器发生异常",e);
        }

        if (key != null) {
            // 拼接文件访问路径。由于拼接的字符串大多为String对象，而不是""的形式，所以直接用+拼接的方式没有优势
            StringBuilder path = new StringBuilder();
            path.append(cloudStorage.getProtocol()).append("://").append(cloudStorage.getBucket()).append(".").append(cloudStorage.getRegion()).append(".").append(cloudStorage.getEndpoint()).append("/").append(key);
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

    /**
     *  ObjectApiBuilder 认证令牌配置
     *
     * @author gclm
     * @param: secretId
     * @param: secretKey
     * @param: region
     * @param: endpoint
     * @date 2020/3/19 9:48 上午
     * @return: cn.ucloud.ufile.api.object.ObjectApiBuilder
     * @throws
     */
    public ObjectApiBuilder build(String secretId,String secretKey,String region,String endpoint){

        ObjectAuthorization auth = new UfileObjectLocalAuthorization(secretId, secretKey);

        ObjectConfig config = new ObjectConfig(region, endpoint);

        /**
         * 配置UfileClient，必须在使用UfileClient之前调用
         */
        ExecutorService executorService = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));

        UfileClient.configure(new UfileClient.Config(
                new HttpClient.Config(10, 5, TimeUnit.MINUTES)
                        .setTimeout(10 * 1000L, 30 * 1000L, 30 * 1000L)
                        .setExecutorService(executorService)));

        return UfileClient.object(auth, config);
    }
}
