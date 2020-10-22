package club.gclmit.chaos.core.io;

import club.gclmit.chaos.core.collection.ArrayUtils;
import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.lang.text.DigestUtils;
import club.gclmit.chaos.core.lang.text.StringUtils;
import java.io.*;

/**
 * <p>
 * 文件辅助工具类
 * </p>
 *
 * @author gclm
 */
public class FileUtils {

    private FileUtils() {
    }

    /**
     * <p>判断文件是否为空 {@code null}.
     *
     * @param file 判断文件
     * @return {@code true} if the file is empty or {@code null}
     */
    public static boolean isEmpty(File file) {
        return file == null || file.isDirectory() || !file.exists();
    }

    /**
     * <p>判断文件是否不为空
     *
     * @param file 判断文件
     * @return {@code true} 当前 file 不为空返回 true
     */
    public static boolean isNotEmpty(File file) {
        return !isEmpty(file);
    }

    /**
     * 效验文件器
     * 功能如果判断文件是否存在，如果不存在先创建文件多层目录，然后创建文件
     *
     * @param filePath 文件路径
     * @author 孤城落寞
     * @return java.io.File
     */
    public static File autoJudgeFile(String filePath) {
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
            throw new ChaosCoreException("创建文件失败,文件不存在", e);
        }

        return null;
    }

    /**
     * <p>
     *  获取项目根目录
     * </p>
     *
     * @author gclm
     * @return java.lang.String
     */
    public static String getRootPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 单个文件删除
     *
     * @param filePath 文件路径
     * @author 孤城落寞
     */
    public static void delete(String filePath) {
        Assert.notNull(filePath, "文件路径不能为空");
        delete(new File(filePath));
    }

    /**
     * 文件删除
     *
     * @param file 文件
     * @author 孤城落寞
     */
    public static void delete(File file) {
        Assert.isTrue(file.exists(), "文件对象不能为空");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delete(f);
            }
        }
        if (!file.delete()) {
            throw new ChaosCoreException("文件:" + file.getName() + " 删除失败");
        }
    }

    /**
     * 批量删除文件
     * @param files 文件集合
     */
    public static void delete(File... files) {
        Assert.notEmpty(files, "文件集合不能为空");
        if (files.length > 1) {
            for (File file : files) {
                if (file.exists()) {
                    delete(file);
                }
            }
        } else if (files.length == 1) {
            delete(files[0]);
        }
    }

    /**
     * 获取文件后缀
     *
     * @param file File
     * @author 孤城落寞
     * @return java.lang.String
     */
    public static String getSuffix(File file) {
        Assert.isTrue(file.exists(), "文件不能为空");
        return getSuffix(file.getName());
    }

    /**
     * 获取文件后缀
     *
     * @author gclm
     * @param fileName 文件名
     * @return java.lang.String
     */
    public static String getSuffix(String fileName) {
        Assert.isTrue(StringUtils.isNotBlank(fileName), "文件名不能为空");
        return StringUtils.substringAfter(fileName, ".");
    }

    /**
     *  判断文件是否相同
     *
     * @author gclm
     * @param file1  文件1
     * @param file2  文件2
     * @return 如果文件相同返回 true,否则返回 false
     */
    public static boolean checkFile(File file1,File file2){
        return DigestUtils.md5Hex(file1).equals(DigestUtils.md5Hex(file2)) && DigestUtils.sha1Hex(file1).equals(DigestUtils.sha1Hex(file2));
    }

    /**
     * 获取文件内容
     *
     * @author gclm
     * @param file File
     * @return java.lang.String
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
     * @author gclm
     * @param file File
     * @return java.lang.String
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
     * @author 孤城落寞
     * @return java.lang.String
     */
    public static String getContentType(File file) {
        Assert.isTrue(file.exists(), "文件不能为空");
        String fileHeader = getFileHeader(file);

        if (!StringUtils.isEmpty(fileHeader)) {
            /**
             * 魔数判断
             */
            String mimeType = FileType.getMimeType(fileHeader.toUpperCase());
            if (MimeType.DEFAULT_FILE_CONTENT_TYPE.equals(mimeType)) {
                String suffix = getSuffix(file);
                mimeType = MimeType.getMime(suffix);
            }
            return mimeType;
        }
        return MimeType.DEFAULT_FILE_CONTENT_TYPE;
    }

    /**
     * 效验文件类型
     *
     * @author gclm
     * @param file 效验文件
     * @return java.lang.String
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
     * @author gclm
     * @param file File
     * @return java.lang.String
     */
    private static String getFileHeader(File file) {
        byte[] b = new byte[28];
        try (
                InputStream inputStream = new FileInputStream(file);
        ) {
            inputStream.read(b, 0, 28);
        } catch (Exception e) {
            throw new ChaosCoreException("读取文件失败", e);
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
        Assert.isFalse(ArrayUtils.isEmpty(bytes), "字节数组不能为空");
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