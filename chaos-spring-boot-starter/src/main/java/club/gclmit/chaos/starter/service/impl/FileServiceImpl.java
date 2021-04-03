package club.gclmit.chaos.starter.service.impl;

import club.gclmit.chaos.core.io.FileUtils;
import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.core.util.UploadFileUtils;
import club.gclmit.chaos.starter.mapper.FileMapper;
import club.gclmit.chaos.starter.service.FileService;
import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.contants.FileStatus;
import club.gclmit.chaos.storage.pojo.FileInfo;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 文件服务接口
 * </p>
 *
 * @author 孤城落寞
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileInfo> implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired(required = false)
    private StorageClient storageClient;

    /**
     * 上传文件
     *
     * @param file MultipartFile
     * @author gclm
     * @return: club.gclmit.chaos.storage.properties.FileInfo
     */
    @Override
    public FileInfo uploadFile(MultipartFile file) {
        File tempFile = UploadFileUtils.multipartFileToFile(file, "");
        String md5 = SecureUtil.md5(tempFile);
        FileInfo fileInfo = queryMd5(md5);
        if (fileInfo == null) {
            fileInfo = storageClient.upload(tempFile);
            if (StringUtils.isNotBlank(fileInfo.getUrl())) {
                save(fileInfo);
            }
        }
        FileUtils.del(tempFile);
        return fileInfo;
    }

    /**
     * 根据文件 MD5 判断文件是否存在
     *
     * @param md5 文件 MD5
     * @return club.gclmit.chaos.storage.db.pojo.FileInfo
     * @author gclm
     */
    @Override
    public FileInfo queryMd5(String md5) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(FileInfo::getMd5, md5);
        return fileMapper.selectOne(queryWrapper);
    }

    /**
     * 根据文件 key 查看 FileInfo 对象
     *
     * @param key OSS Key
     * @return club.gclmit.chaos.storage.db.pojo.FileInfo
     * @author gclm
     */
    @Override
    public FileInfo queryKey(String key) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(FileInfo::getOssKey, key);
        return fileMapper.selectOne(queryWrapper);
    }

    /**
     * 根据 key 删除文件
     *
     * @param key OSS Key
     * @author gclm
     */
    @Override
    public void deleteStatusByKey(String key) {
        removeById(queryKey(key).getId());
    }

    /**
     * 根据 key 批量删除文件
     *
     * @param keys OSS Key
     * @author gclm
     */
    @Override
    public void batchDeleteStatusByKey(List<String> keys) {
        for (String key : keys) {
            deleteStatusByKey(key);
        }
    }

    /**
     * 根据 key 修改文件状态
     *
     * @param key OSS Key
     * @author gclm
     */
    @Override
    public void updateStatusByKey(String key) {
        UpdateWrapper<FileInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(FileInfo::getOssKey, key);

        fileMapper.update(new FileInfo(FileStatus.OSS_FILE_FAIL.getId()), updateWrapper);
    }

    /**
     * 根据 key 批量修改文件状态
     *
     * @param keys OSS Key
     * @author gclm
     */
    @Override
    public void batchUpdateStatusByKey(List<String> keys) {
        for (String key : keys) {
            updateStatusByKey(key);
        }
    }

    /**
     * 根据OSS key 模糊查询
     *
     * @param key OSS Key
     * @author gclm
     * @return: FileInfo List
     */
    @Override
    public List<FileInfo> linkQueryKey(String key) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(FileInfo::getOssKey, key);
        return fileMapper.selectList(queryWrapper);
    }

    /**
     * 根据文件名字模糊查询
     *
     * @param fileName 文件名字
     * @return FileInfo List
     * @author gclm
     */
    @Override
    public List<FileInfo> linkQueryFileName(String fileName) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(FileInfo::getName, fileName);
        return fileMapper.selectList(queryWrapper);
    }

    /**
     * 根据文件大小区间查询
     *
     * @param startSize 最小
     * @param endSize   最大
     * @return FileInfo List
     * @author gclm
     */
    @Override
    public List<FileInfo> queryFileSizeBetween(Long startSize, Long endSize) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .between(FileInfo::getSize, startSize, endSize);
        return fileMapper.selectList(queryWrapper);
    }
}
