package club.gclmit.chaos.storage.client;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.io.FileUtils;
import club.gclmit.chaos.core.io.MimeType;
import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.storage.model.FileInfo;
import club.gclmit.chaos.storage.model.Storage;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.util.Assert;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 抽象存储类
 * </p>
 *
 * @author gclm
 */
public abstract class StorageClient {

    /**
     * 云存储配置信息
     */
    protected Storage storage;

    public StorageClient(Storage storage) {
        this.storage = storage;
    }

    /**
     * 文件路径
     *
     * 这里采用的文件命名格式：时间段 + 雪花算法id
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {



        LocalDate localDate = LocalDate.now();
        String dateFormat = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);

        //文件路径
        StringBuilder path = new StringBuilder();

        /**
         * 前后缀拼接逻辑:
         *  先判断是否存在前缀，存在在拼接，否则根据实际情况进行修改
         */
        if (StringUtils.isNotBlank(prefix)) {
            path.append(prefix).append("/");
        }

        path.append(dateFormat).append("/").append(IdUtil.fastSimpleUUID());

        if (suffix != null) {
            path = suffix.contains(".") ? path.append(suffix) : path.append(".").append(suffix);
        }

        return path.toString();
    }

    /**
     * <p>
     *  上传文件
     * </p>
     *
     * @author 孤城落寞
     * @param file 文件
     * @return java.lang.String
     */
    public FileInfo upload(File file) {
        Assert.isTrue(file.exists(),"上传文件不能为空");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            /**
             * 根据工具类获取 fileInfo 参数
             */
            String key = getPath(storage.getConfig().getPrefix(), FileUtils.getSuffix(file));
            String contentType = FileUtils.getContentType(file);
            String md5 = SecureUtil.md5(file);
            return upload(fileInputStream,new FileInfo(file.getName(),contentType,file.length(), md5,key,storage.getType().getId()));
        } catch (Exception e) {
            throw new ChaosException("文件上传失败",e);
        }
    }

    /**
     * <p>
     *  上传字节数组
     * </p>
     *
     * @author 孤城落寞
     * @param data      字节数组
     * @param key       文件路径
     * @param fileName  文件名
     * @return java.lang.String 文件路径
     */
    public FileInfo upload(byte[] data, String key, String fileName) {
        Assert.notEmpty(Collections.singleton(data),"上传文件失败，请检查 byte[] 是否正常");
        Assert.hasLength(key,"上传文件失败，请检查上传文件的 key 是否正常");

        /**
         * 根据工具类获取 fileInfo 参数
         */
        String contentType = MimeType.TXT.getMimeType();
        String md5  = SecureUtil.md5(Arrays.toString(data));
        Long size = Long.valueOf(String.valueOf(data.length));

        /**
         * 如果不制定文件名,则key为文件名
         */
        if (StringUtils.isBlank(fileName)) {
            fileName = key;
        }

        return upload(new ByteArrayInputStream(data),new FileInfo(fileName,contentType,size, md5,key,storage.getType().getId()));
    }

    /**
     *  上传字符串
     *
     * @author gclm
     * @param content     字符串内容
     * @param key         key
     * @param fileName    文件名
     * @return club.gclmit.chaos.storage.db.pojo.FileInfo
     */
    public FileInfo upload(String content,String key,String fileName){

        Assert.hasLength(key,"上传文件失败，请检查上传文件的 content 是否正常");
        Assert.hasLength(key,"上传文件失败，请检查上传文件的 key 是否正常");

        if (StringUtils.isBlank(key)) {
            key = DateUtils.getMilliTimestamp() + ".txt";
        }
        try {
            return upload(content.getBytes("UTF-8"),key,fileName);
        } catch (UnsupportedEncodingException e) {
            throw new ChaosException("String --> Bytes 编码出现问题");
        }
    }

    /**
     * 上传文件使用默认配置
     * @param inputStream InputStream
     * @param fileInfo 文件消息
     * @return club.gclmit.chaos.storage.db.pojo.FileInfo
     */
    public abstract FileInfo upload(InputStream inputStream, FileInfo fileInfo);

    /**
     * 批量删除
     * @param keys 文件keys
     */
    public abstract void delete(List<String> keys);

    /**
     * 删除单个
     * @param key 文件key
     */
    public abstract void delete(String key);

}
