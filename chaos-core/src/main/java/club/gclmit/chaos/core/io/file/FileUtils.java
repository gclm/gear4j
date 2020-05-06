
package club.gclmit.chaos.core.io.file;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.util.ArrayUtils;
import club.gclmit.chaos.core.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Clock;

/**
 * <p>
 * 文件辅助工具类
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/29 4:34 下午
 * @version: V1.0
 * @since 1.8
 */
public class FileUtils {

    /**
     * <p>判断文件是否为空 {@code null}.
     *
     * @param file  判断文件
     * @return {@code true} if the file is empty or {@code null}
     */
    public static boolean isEmpty(File file) {
        return file == null || !file.exists();
    }

    /**
     * <p>判断文件是否不为空
     *
     * @param file  判断文件
     * @return {@code true} 当前 file 不为空返回 true
     */
    public static boolean isNotEmpty(File file) {
        return !isEmpty(file);
    }

    /**
     * 效验文件器 --> 功能如果判断文件是否存在，如果不存在先创建文件多层目录，然后创建文件
     *
     * @param filePath
     * @author 孤城落寞
     * @date: 2019-08-19
     * @return: java.io.File
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
     * 单个文件删除
     *
     * @param filePath
     * @author 孤城落寞
     * @date: 2019-08-19
     * @return: void
     */
    public static void deleteFile(String filePath) {
        Assert.notNull(filePath, "文件路径不能为空");
        delete(new File(filePath));
    }

    /**
     * 单个文件删除
     *
     * @param file
     * @author 孤城落寞
     * @date: 2019-08-19
     * @return: void
     */
    public static void delete(File file) {
        Assert.isTrue(file.exists(), "文件对象不能为空");
        if (!file.delete()){
            throw new ChaosCoreException("文件:"+file.getName()+" 删除失败");
        }
    }

    /**
     * 批量删除文件
     *
     * @param files
     * @return void
     * @details 孤城落寞 2019-02-14 10:08
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
     * MultipartFile 转 File
     *
     * @param multipartFile
     * @return java.io.File
     * @details 孤城落寞 2019-02-14 10:16
     */
    public static File multipartFileToFile(String folder, MultipartFile multipartFile) {
        Assert.notNull(multipartFile.isEmpty(), "multipartFile 不能为空");

        if (StringUtils.isEmpty(folder)) {
            folder = System.getProperty("user.dir");
        }

        File localFile = new File(folder, new StringBuilder().append(Clock.systemDefaultZone().millis()).append(".").append(getSuffix(multipartFile)).toString());

        try {
            multipartFile.transferTo(localFile);
        } catch (IOException e) {
            throw new ChaosCoreException("MultipartFile To File 失败", e);
        }

        return localFile;
    }

    /**
     * 获取文件后缀
     *
     * @param file
     * @author 孤城落寞
     * @return: java.lang.String
     */
    public static String getSuffix(MultipartFile file) {
        Assert.notNull(file, "文件不能为空");
        return getSuffix(file.getOriginalFilename());
    }

    /**
     * 获取文件后缀
     *
     * @param file
     * @author 孤城落寞
     * @return: java.lang.String
     */
    public static String getSuffix(File file) {
        Assert.isTrue(file.exists(), "文件不能为空");
        return getSuffix(file.getName());
    }

    /**
     *  获取文件后缀
     *
     * @author gclm
     * @param: fileName
     * @date 2020/4/21 7:18 下午
     * @return: java.lang.String
     */
    public static String getSuffix(String fileName) {
        Assert.isTrue(StringUtils.isNotBlank(fileName),"文件名不能为空");
        return StringUtils.subAfter(fileName,".",true);
    }

    /**
     * 获取文件内容
     *
     * @throws
     * @author gclm
     * @param: file
     * @date 2020/2/29 1:17 下午
     * @return: java.lang.String
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
     * @throws
     * @author gclm
     * @param: file
     * @date 2020/2/29 1:17 下午
     * @return: java.lang.String
     */
    public static String getContentTrim(File file) {
        return getContent(file).replaceAll("\\s*", "");
    }

    /**
     * 基于魔数和文件后缀获取内容类型
     * 1. 先进行魔数筛选
     * 2. 根据魔数筛选结果,进行后缀获取内容类型
     *
     * @param file
     * @author 孤城落寞
     * @date: 2019/9/23
     * @return: java.lang.String
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
     * @throws
     * @author gclm
     * @param: file
     * @date 2020/3/2 2:49 下午
     * @return: java.lang.String
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
     * @throws
     * @title getFileHeader
     * @author gclm
     * @param: filePath 文件路径
     * @date 2019/11/6 23:48
     * @return: java.lang.String
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
     * @param src
     * @return
     */
    private static String byteToHex(byte[] src) {
        Assert.isFalse(ArrayUtils.isEmpty(src),"字节数组不能为空");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}