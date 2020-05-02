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
 * @since 2019-12-17
 */
public interface FileService extends IService<FileInfo> {

    /**
     *  上传文件到 OSS中
     *
     * @author gclm
     * @param: file
     * @date 2020/3/17 9:13 上午
     * @return: club.gclmit.chaos.storage.db.pojo.FileInfo
     * @throws
     */
    public FileInfo uploadFile(MultipartFile file);

    /**
     *  根据文件 MD5 判断文件是否存在
     *
     * @author gclm
     * @param: md5
     * @date 2020/3/17 9:13 上午
     * @return: club.gclmit.chaos.storage.db.pojo.FileInfo
     * @throws
     */
    public FileInfo queryMd5(String md5);


    /**
     *  根据OSS key 查询
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 9:14 上午
     * @return: java.util.List<club.gclmit.chaos.storage.db.pojo.FileInfo>
     * @throws
     */
    public FileInfo queryKey(String key);

    /**
     *  根据OSS key 修改文件状态
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 9:14 上午
     * @throws
     */
    public void updateStatusByKey(String key);

    /**
     *  根据OSS keys 批量修改文件状态
     *
     * @author gclm
     * @param: keys
     * @date 2020/3/17 9:14 上午
     * @throws
     */
    public void batchUpdateStatusByKey(List<String> keys);


    /**
     *  根据OSS key 删除文件
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 9:14 上午
     * @throws
     */
    public void deleteStatusByKey(String key);

    /**
     *  根据OSS keys 批量删除文件
     *
     * @author gclm
     * @param: keys
     * @date 2020/3/17 9:14 上午
     * @throws
     */
    public void batchDeleteStatusByKey(List<String> keys);


    /**
     *  根据OSS key 模糊查询
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 9:14 上午
     * @return: java.util.List<club.gclmit.chaos.storage.db.pojo.FileInfo>
     * @throws
     */
    public List<FileInfo> linkQueryKey(String key);

    /**
     *  根据文件名字模糊查询
     *
     * @author gclm
     * @param: fileName
     * @date 2020/3/17 9:14 上午
     * @return: java.util.List<club.gclmit.chaos.storage.db.pojo.FileInfo>
     * @throws
     */
    public List<FileInfo> linkQueryFileName(String fileName);

    /**
     *  根据文件大小区间查询
     *
     * @author gclm
     * @param: startSize
     * @param: endSize
     * @date 2020/3/17 9:15 上午
     * @return: java.util.List<club.gclmit.chaos.storage.db.pojo.FileInfo>
     * @throws
     */
    public List<FileInfo> queryFileSizeBetween(Long startSize, Long endSize);

}
