package club.gclmit.chaos.core.io;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.util.StringUtils;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 获取文件类型
 *
 * @author gclm
 * @since 1.8
 */
public class FileTypeUtils {


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
            String mimeType = FileType.getMimeType(fileHeader.toUpperCase());
            if (MimeType.DEFAULT_FILE_CONTENT_TYPE.equals(mimeType)) {
                String suffix = FileNameUtil.getSuffix(file);
                mimeType = MimeType.getMimeTypeBySuffix(suffix);
            }
            return mimeType;
        }
        return MimeType.DEFAULT_FILE_CONTENT_TYPE;
    }


    /**
     * 根据Mine获取文档的 后缀
     *
     * @author gclm
     * @param mime Mine
     * @return java.lang.String
     */
    public static String getSuffix(String mime) {
        Assert.isTrue(StringUtils.isNotBlank(mime), "MimeType不能为空");
        return MimeType.getSuffixByMimeType(mime);
    }


    /**
     * 效验文件类型
     *
     * @param file 效验文件
     * @return java.lang.String
     * @author gclm
     */
    public static String judgeFileType(File file) {
        Assert.isTrue(file.exists(), "文件不能为空");
        String fileHeader = getFileHeader(file);
        if (!StringUtils.isEmpty(fileHeader)) {
            return FileType.getKey(fileHeader.toUpperCase());
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
