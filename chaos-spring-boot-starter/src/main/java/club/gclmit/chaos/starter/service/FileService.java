package club.gclmit.chaos.starter.service;

import club.gclmit.chaos.storage.properties.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  文件服务类
 * </p>
 *
 * @author 孤城落寞
 */
public interface FileService extends IService<FileInfo> {

    /**
     *  上传文件到 OSS中
     *
     * @author gclm
     * @param file MultipartFile
     * @return club.gclmit.chaos.storage.db.pojo.FileInfo
     */
    public FileInfo uploadFile(MultipartFile file);

    /**
     *  根据文件 MD5 判断文件是否存在
     *
     * @author gclm
     * @param md5 md5
     * @return club.gclmit.chaos.storage.db.pojo.FileInfo
     */
    public FileInfo queryMd5(String md5);


    /**
     *  根据OSS key 查询
     *
     * @author gclm
     * @param key OSS key
     * @return FileInfo List
     */
    public FileInfo queryKey(String key);

    /**
     *  根据OSS key 模糊查询
     *
     * @author gclm
     * @param key OSS key
     * @return FileInfo List
     */
    public List<FileInfo> linkQueryKey(String key);

    /**
     *  根据文件名字模糊查询
     *
     * @author gclm
     * @param fileName 文件名字
     * @return FileInfo List
     */
    public List<FileInfo> linkQueryFileName(String fileName);

    /**
     *  根据文件大小区间查询
     *
     * @author gclm
     * @param startSize  最小
     * @param endSize    最大
     * @return: FileInfo List
     */
    public List<FileInfo> queryFileSizeBetween(Long startSize, Long endSize);

    /**
     *  根据OSS key 修改文件状态
     *
     * @author gclm
     * @param key OSS key
     */
    public void updateStatusByKey(String key);

    /**
     *  根据OSS keys 批量修改文件状态
     *
     * @author gclm
     * @param keys OSS key
     */
    public void batchUpdateStatusByKey(List<String> keys);


    /**
     *  根据OSS key 删除文件
     *
     * @author gclm
     * @param key OSS key
     */
    public void deleteStatusByKey(String key);

    /**
     *  根据OSS keys 批量删除文件
     *
     * @author gclm
     * @param keys OSS key
     */
    public void batchDeleteStatusByKey(List<String> keys);

}
