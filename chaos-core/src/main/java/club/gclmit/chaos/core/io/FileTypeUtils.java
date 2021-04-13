package club.gclmit.chaos.core.io;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.utils.StringUtils;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 文件类型工具类
 *
 * @author gclm
 * @since 1.8
 */
public class FileTypeUtils {

    /**
     * 图片
     */
    public static final String[] IMAGE_EXTENSION = {"bmp", "gif", "jpg", "jpeg", "png"};

    /**
     * flash
     */
    public static final String[] FLASH_EXTENSION = {"swf", "flv"};

    /**
     * media 后缀
     */
    public static final String[] MEDIA_EXTENSION = {"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb"};

    /**
     * video 后缀
     */
    public static final String[] VIDEO_EXTENSION = {"mp4", "avi", "rmvb"};

    /**
     * 默认允许的后缀名
     */
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb",
            // pdf
            "pdf"};

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
        String fileHeader = getFileHeader(file);

        if (!StringUtils.isEmpty(fileHeader)) {
            /**
             * 魔数判断
             */
            String mimeType = MagicType.getMimeType(fileHeader.toUpperCase());
            if (MimeType.DEFAULT_FILE_CONTENT_TYPE.equals(mimeType)) {
                String suffix = FileNameUtil.getSuffix(file);
                mimeType = MimeType.getMimeTypeBySuffix(suffix);
            }
            return mimeType;
        }
        return MimeType.DEFAULT_FILE_CONTENT_TYPE;
    }


    /**
     * 获取文件类型
     * <p>
     * 例如: chaos.txt, 返回: txt
     *
     * @param file 文件名
     * @return 后缀（不含".")
     */
    public static String getSuffix(File file) {
        Assert.notNull(file, "文件不能为空");
        return getSuffix(file.getName());
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
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        int separatorIndex = fileName.lastIndexOf(".");
        if (separatorIndex < 0) {
            return "";
        }
        return StringUtils.subAfter(fileName,".",true);
    }

    /**
     * 获取文件类型
     * <p>
     * 例如: chaos.txt, 返回: txt
     *
     * @param fileName 文件名
     * @return 后缀（不含".")
     */
    public static String getSuffix(String fileName) {
        int separatorIndex = fileName.lastIndexOf(".");
        if (separatorIndex < 0) {
            return "";
        }
        return StringUtils.subAfter(fileName,".",true);
    }

    /**
     * 根据Mine获取文档的 后缀
     *
     * @param mime Mine
     * @return 后缀（不含".")
     * @author gclm
     */
    public static String getSuffixByMimeType(String mime) {
        Assert.isTrue(StringUtils.isNotBlank(mime), "MimeType不能为空");
        return MimeType.getSuffixByMimeType(mime);
    }

    /**
     * 根据Magic获取文档的 后缀
     *
     * @param file 效验文件
     * @return 后缀（不含".")
     * @author gclm
     */
    public static String getSuffixByMagic(File file) {
        Assert.isTrue(file.exists(), "文件不能为空");
        String fileHeader = getFileHeader(file);
        if (!StringUtils.isEmpty(fileHeader)) {
            return MagicType.getSuffix(fileHeader.toUpperCase());
        }
        return null;
    }

    /**
     * <p>
     * 获取文件头部
     * </p>
     *
     * @param file File
     * @return java.lang.String
     * @author gclm
     */
    public static String getFileHeader(File file) {
        byte[] b = new byte[28];
        try (
                InputStream inputStream = new FileInputStream(file);
        ) {
            inputStream.read(b, 0, 28);
        } catch (Exception e) {
            throw new ChaosException("读取文件失败", e);
        }
        return byteToHex(b);
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param bytes 字节数组
     * @return String
     */
    private static String byteToHex(byte[] bytes) {
        Assert.isFalse(ArrayUtil.isEmpty(bytes), "字节数组不能为空");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
