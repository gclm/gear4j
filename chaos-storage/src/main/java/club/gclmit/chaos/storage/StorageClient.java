package club.gclmit.chaos.storage;

import club.gclmit.chaos.core.helper.id.IDHelper;
import club.gclmit.chaos.storage.properties.Storage;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 抽象存储类
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-23 18:38:00
 * @version: V1.0
 * @since JDK1.8
 */
public abstract class StorageClient {

    /**
     * 云存储配置信息
     */
    protected Storage storage;

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

        /**
         * 这里使用雪花算法目的---> 后期可能会将 key 进行 split，然后进行分类统计
         */
        String id = IDHelper.getStringId();

        LocalDate localDate = LocalDate.now();
        String dateFormat = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);

        //文件路径
        String path = new StringBuilder(dateFormat).append("/").append(id).toString();

        /**
         * 前后缀拼接逻辑:
         *  先判断是否存在前缀，存在在拼接，否则根据实际情况进行修改
         */
        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        if (suffix != null) {
            if (suffix.startsWith(".")) {
                path = path + suffix;
            } else {
                path = path + "." + suffix;
            }
        }
        return path;
    }



    /**
     * 批量删除
     */
    public abstract void delete(List<String> keys);

    /**
     * 删除单个
     */
    public abstract void delete(String key);

    /**
     * 上传文件
     */
    public abstract String upload(File file);

    /**
     * 上传文件
     */
    public abstract String upload(byte[] data, String key);

    /**
     * 上传文件使用默认配置
     */
    public abstract String upload(InputStream inputStream, String key);

}
