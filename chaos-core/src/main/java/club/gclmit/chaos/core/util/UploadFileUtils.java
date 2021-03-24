package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.io.FileType;
import club.gclmit.chaos.core.io.FileUtils;
import club.gclmit.chaos.core.io.MimeType;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Spring MVC 文件上传
 *
 * @author gclm
 * @since 2020/10/22 18:51
 * @since 1.8
 */
@UtilityClass
public class UploadFileUtils {

//    FileCopyUtils
//    FileSystemUtils

    /**
     * <p>判断文件是否为空 {@code null}.
     *
     * @param file 判断文件
     * @return {@code true} if the file is empty or {@code null}
     */
    public static boolean isEmpty(MultipartFile file) {
        return file == null || file.isEmpty();
    }

    /**
     * <p>判断文件是否不为空
     *
     * @param file 判断文件
     * @return {@code true} 当前 file 不为空返回 true
     */
    public static boolean isNotEmpty(MultipartFile file) {
        return !isEmpty(file);
    }

    /**
     * MultipartFile 转 File
     *
     * @param multipartFile springmvc封装的上传文件
     * @param folder        文件夹路径
     * @return java.io.File
     */
    public static File multipartFileToFile(MultipartFile multipartFile, String folder) {
        Assert.notNull(multipartFile.isEmpty(), "multipartFile 不能为空");

        folder = StringUtils.isEmpty(folder) ? FileUtils.getRootPath() : folder;
        File localFile = new File(folder, new StringBuilder().append(IdUtil.fastSimpleUUID()).append(".").append(getSuffix(multipartFile)).toString());

        try {
            multipartFile.transferTo(localFile);
        } catch (IOException e) {
            throw new ChaosException("MultipartFile To File 失败", e);
        }

        return localFile;
    }

    /**
     * 获取文件后缀
     *
     * @param file MultipartFile
     * @author 孤城落寞
     * @return java.lang.String
     */
    public static String getSuffix(MultipartFile file) {
        Assert.notNull(file, "文件不能为空");
        return StringUtils.subAfter(file.getOriginalFilename(),".",true);
    }

    /**
     * 基于魔数和文件后缀获取内容类型
     * 1. 先进行魔数筛选
     * 2. 根据魔数筛选结果,进行后缀获取内容类型
     *
     * @param file File
     * @return java.lang.String
     * @author 孤城落寞
     */
    public static String getMimeType(File file) {
        Assert.isTrue(file.exists(), "文件不能为空");
        String fileHeader = FileUtils.getFileHeader(file);

        if (!StringUtils.isEmpty(fileHeader)) {
            /**
             * 魔数判断
             */
            String mimeType = FileType.getMimeType(fileHeader.toUpperCase());
            if (MimeType.DEFAULT_FILE_CONTENT_TYPE.equals(mimeType)) {
                String suffix = FileUtils.getSuffix(file);
                mimeType = MimeType.getMimeTypeBySuffix(suffix);
            }
            return mimeType;
        }
        return MimeType.DEFAULT_FILE_CONTENT_TYPE;
    }

}
