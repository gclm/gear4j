package club.gclmit.chaos.core.io;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.util.StringUtils;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.experimental.UtilityClass;

import java.io.*;

/**
 * <p>
 * 文件辅助工具类
 * </p>
 *
 * @author gclm
 */
@UtilityClass
public class FileUtils extends FileUtil {

    /**
     *  获取项目根目录
     *
     * @author gclm
     * @return java.lang.String
     */
    public static String getRootPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 效验文件器
     * 功能如果判断文件是否存在，如果不存在先创建文件多层目录，然后创建文件
     *
     * @param filePath 文件路径
     * @return java.io.File
     * @author 孤城落寞
     */
    public static File touch(String filePath) {
        Assert.notNull(filePath, "文件路径不能为空");

        /**
         * 创建当前文件和该目录的父文件
         */
        File file = new File(filePath);
        File dirFile = new File(file.getParent());

        /**
         * 判断父文件是否存在，如果不存在则创建多层目录
         */
        if (!dirFile.exists() && !dirFile.isDirectory()) {
            dirFile.mkdirs();
        }

        /**
         * 如果文件不存在则开始创建文件
         */
        try {
            if (!file.exists()) {
                return file.createNewFile() ? file : null;
            }
        } catch (IOException e) {
            throw new ChaosException("创建文件失败,文件不存在", e);
        }

        return null;
    }

    /**
     * 判断文件是否相同
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return 如果文件相同返回 true,否则返回 false
     * @author gclm
     */
    public static boolean checkFile(File file1, File file2) {
        return SecureUtil.md5(file1).equals(SecureUtil.md5(file2)) && SecureUtil.sha1(file1).equals(SecureUtil.sha1(file2));
    }

    /**
     * 获取文件内容
     *
     * @param file File
     * @return java.lang.String
     * @author gclm
     */
    public static String getContent(File file) {
        Assert.isTrue(file.exists(), "文件不能为空");
        StringBuilder str = new StringBuilder();
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
        } catch (Exception e) {
        }
        return str.toString();
    }

    /**
     * 获取文件内容并去除全部空格
     *
     * @param file File
     * @return java.lang.String
     * @author gclm
     */
    public static String getContentTrim(File file) {
        return getContent(file).replaceAll("\\s*", "");
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
        String fileHeader = getFileHeader(file);

        if (!StringUtils.isEmpty(fileHeader)) {
            /**
             * 魔数判断
             */
            String mimeType = FileType.getMimeType(fileHeader.toUpperCase());
            if (MimeType.DEFAULT_FILE_CONTENT_TYPE.equals(mimeType)) {
                String suffix = getSuffix(file);
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
    private static String getFileHeader(File file) {
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