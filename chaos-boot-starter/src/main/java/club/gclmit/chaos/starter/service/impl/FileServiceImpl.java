package club.gclmit.chaos.starter.service.impl;

import club.gclmit.chaos.core.encrypt.MD5Utils;
import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.io.file.FileUtils;
import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.util.IDUtils;
import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.starter.mapper.FileMapper;
import club.gclmit.chaos.starter.service.FileService;
import club.gclmit.chaos.storage.client.StorageClient;
import club.gclmit.chaos.storage.properties.FileInfo;
import club.gclmit.chaos.storage.properties.FileStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.util.List;

/**
 * <p>
 *  文件服务接口
 * </p>
 *
 * @author 孤城落寞
 * @since 2019-12-17
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileInfo> implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private StorageClient storageClient;

    /**
     *  上传文件
     *
     * @author gclm
     * @param: file
     * @date 2020/3/24 3:17 下午
     * @return: club.gclmit.chaos.storage.properties.FileInfo
     * @throws
     */
    @Override
    public FileInfo uploadFile(MultipartFile file) {
        File tempFile = multipartFileToFile(file);
        String md5 = new MD5Utils().encode(tempFile);
        FileInfo fileInfo = queryMD5(md5);
        if (fileInfo == null) {
            fileInfo = storageClient.upload(tempFile);
            save(fileInfo);
        }
        FileUtils.deleteFile(tempFile);
        return fileInfo;
    }

    /**
     *  根据文件 MD5 判断文件是否存在
     *
     * @author gclm
     * @param: md5
     * @date 2020/3/17 9:13 上午
     * @return: club.gclmit.chaos.storage.db.pojo.FileInfo
     * @throws
     */
    @Override
    public FileInfo queryMD5(String md5) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(FileInfo::getMd5,md5);
        return fileMapper.selectOne(queryWrapper);
    }

    /**
     *  根据文件 key 查看 FileInfo 对象
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 10:42 上午
     * @return: club.gclmit.chaos.storage.db.pojo.FileInfo
     * @throws
     */
    @Override
    public FileInfo queryKey(String key) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(FileInfo::getOssKey,key);
        return fileMapper.selectOne(queryWrapper);
    }

    /**
     *  根据 key 删除文件
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 11:18 上午
     * @throws
     */
    @Override
    public void deleteStatusByKey(String key) {
        removeById(queryKey(key).getId());
    }

    /**
     *  根据 key 批量删除文件
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 11:18 上午
     * @throws
     */
    @Override
    public void batchDeleteStatusByKey(List<String> keys) {
        for (String key : keys) {
            deleteStatusByKey(key);
        }
    }

    /**
     *  根据 key 修改文件状态
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 11:18 上午
     * @throws
     */
    @Override
    public void updateStatusByKey(String key) {
        UpdateWrapper<FileInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(FileInfo::getOssKey,key);

        fileMapper.update(new FileInfo(FileStatus.OSS_FILE_FAIL.getId()), updateWrapper);
    }

    /**
     *  根据 key 批量修改文件状态
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 11:18 上午
     * @throws
     */
    @Override
    public void batchUpdateStatusByKey(List<String> keys) {
        for (String key : keys) {
            updateStatusByKey(key);
        }
    }

    /**
     *  根据OSS key 模糊查询
     *
     * @author gclm
     * @param: key
     * @date 2020/3/17 9:14 上午
     * @return: java.util.List<club.gclmit.chaos.storage.db.pojo.FileInfo>
     * @throws
     */
    @Override
    public List<FileInfo> linkQueryKey(String key) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(FileInfo::getOssKey,key);
        return fileMapper.selectList(queryWrapper);
    }

    /**
     *  根据文件名字模糊查询
     *
     * @author gclm
     * @param: fileName
     * @date 2020/3/17 9:14 上午
     * @return: java.util.List<club.gclmit.chaos.storage.db.pojo.FileInfo>
     * @throws
     */
    @Override
    public List<FileInfo> linkQueryFileName(String fileName) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(FileInfo::getName,fileName);
        return fileMapper.selectList(queryWrapper);
    }

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
    @Override
    public List<FileInfo> queryFileSizeBetween(Long startSize, Long endSize) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .between(FileInfo::getSize,startSize,endSize);
        return fileMapper.selectList(queryWrapper);
    }

    public File multipartFileToFile(MultipartFile multipartFile){
        Assert.notNull(multipartFile.isEmpty(),"multipartFile 不能为空");

        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.indexOf("."));

        File localFile = new File(new StringBuilder().append(IDUtils.snowflakeId()).append(suffix).toString());

        try {
            multipartFile.transferTo(localFile);
        } catch (IOException e) {
            throw new ChaosCoreException("MultipartFile To File 失败",e);
        }
        return localFile;
    }
}
