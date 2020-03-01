
package club.gclmit.chaos.core.helper.file;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Clock;
import java.util.Properties;

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
public class FileHelper {

    public static final String DEFAULT_FILE_CONTENT_TYPE = "application/octet-stream";

    /**
     * 效验文件器 --> 功能如果判断文件是否存在，如果不存在先创建文件多层目录，然后创建文件
     * @author 孤城落寞
     * @param filePath
     * @date: 2019-08-19
     * @return: java.io.File
     */
    public static File autoJudgeFile(String filePath) throws IOException {
        Assert.notNull(filePath,"文件路径不能为空");

        /**
         * 创建当前文件和该目录的父文件
         */
        File file = new File(filePath);
        File dirFile = new File(file.getParent());

        /**
         * 判断父文件是否存在，如果不存在则创建多层目录
         */
        if(!dirFile.exists() && !dirFile.isDirectory()){
            dirFile.mkdirs();
        }

        /**
         * 如果文件不存在则开始创建文件
         */
        if(!file.exists()){
            file.createNewFile();
            return file;
        }

        return null;
    }

    /**
     * 单个文件删除
     * @author 孤城落寞
     * @param filePath
     * @date: 2019-08-19
     * @return: void
     */
    public static void deleteFile(String filePath){
        Assert.notNull(filePath,"文件路径不能为空");
        new File(filePath).delete();
    }

    /**
     * 单个文件删除
     * @author 孤城落寞
     * @param file
     * @date: 2019-08-19
     * @return: void
     */
    public static void deleteFile(File file){
        Assert.isTrue(file.exists(),"文件对象不能为空");
        file.delete();
    }

    /**
     * 批量删除文件
     * @details 孤城落寞 2019-02-14 10:08
     * @param files
     * @return void
     */
    public static void deleteFiles(File... files){
        Assert.notEmpty(files,"文件集合不能为空");
        if (files.length > 1){
            for (File file : files){
                if (file.exists()){
                    file.delete();
                }
            }
        } else if (files.length == 1){
            files[0].delete();
        }
    }

    /**
     *  MultipartFile 转 File
     * @details 孤城落寞 2019-02-14 10:16
     * @param multipartFile
     * @return java.io.File
     */
    public static File multipartFileToFile(String folder,MultipartFile multipartFile) throws IOException {
        Assert.notNull(multipartFile.isEmpty(),"multipartFile 不能为空");

        if (StringUtils.isEmpty(folder)) {
            folder = System.getProperty("user.dir");
        }

        File localFile = new File(folder, new StringBuilder().append(Clock.systemDefaultZone().millis()).append(".").append(getSuffix(multipartFile)).toString());

        multipartFile.transferTo(localFile);

        return localFile;
    }

    /**
     * 获取文件后缀
     * @author 孤城落寞
     * @param file
     * @return: java.lang.String
     */
    public static String getSuffix(File file){
        Assert.notNull(file,"文件不能为空");
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    /**
     * 获取文件后缀
     * @author 孤城落寞
     * @param multipartFile
     * @return: java.lang.String
     */
    public static String getSuffix(MultipartFile multipartFile){
        Assert.notNull(multipartFile,"文件不能为空");
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.indexOf(".")+1);
        return suffix;
    }


    /**
     * 获取文件类型
     * @author 孤城落寞
     * @param filePath
     * @date: 2019/9/23
     * @return: java.lang.String
     */
    public static FileType getFileType(String filePath) throws IOException {
        Assert.notNull(filePath,"文件路径不能为空");
        String fileHeader = getFileHeader(filePath);
        if (null == fileHeader || fileHeader.length() == 0){
            return null;
        }

        /**
         * 魔数判断
         */
        fileHeader = fileHeader.toUpperCase();
        FileType[] fileTypes = FileType.values();
        for (FileType type: fileTypes) {
            if (fileHeader.startsWith(type.getValue())) {
                return type;
            }
        }
        return null;
    }

    /**
     *  获取文件内容
     *
     * @author gclm
     * @param: file
     * @date 2020/2/29 1:17 下午
     * @return: java.lang.String
     * @throws
     */
    public static String getFileContent(File file){
        StringBuilder str = new StringBuilder();
        try(
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
        ){
            String line;
            while ((line = reader.readLine()) != null){
                str.append(line);
            }
        } catch (Exception e) {
        }
        return str.toString();
    }

    /**
     *  获取文件内容并去除全部空格
     *
     * @author gclm
     * @param: file
     * @date 2020/2/29 1:17 下午
     * @return: java.lang.String
     * @throws
     */
    public static String getFileContentNotTrim(File file){
        StringBuilder str = new StringBuilder();
        try(
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
        ){
            String line;
            while ((line = reader.readLine()) != null){
                str.append(line);
            }
        } catch (Exception e) {
        }
        return str.toString().replaceAll("\\s*", "");
    }

    /**
     * <p>
     *  文件内容类型
     * </p>
     *
     * @author gclm
     * @param: path
     * @date 2019/12/3 9:07 上午
     * @return: java.lang.String
     * @throws
     */
    public static String getFileContentType(String path) {
        Assert.notNull(path,"文件路径不能为空");
        String suffix = path.substring(path.indexOf("."));
        Properties props = new Properties();
        ResourcesPath resourcesPath = new ResourcesPath();

        try {
            props.load(resourcesPath.read("content-type.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object result = props.get(suffix);
        if (result == null) {
            return DEFAULT_FILE_CONTENT_TYPE;
        }
        return result.toString();
    }

    /**
     * <p>
     *  获取文件头部
     * </p>
     *
     * @title getFileHeader
     * @author gclm
     * @param: filePath 文件路径
     * @date 2019/11/6 23:48
     * @return: java.lang.String
     * @throws
     */
    private static String getFileHeader(String filePath) throws IOException {
        byte[] b = new byte[28];
        InputStream inputStream = new FileInputStream(filePath);
        inputStream.read(b,0,28);
        inputStream.close();
        return byteToHex(b);
    }

    /**
     * 将字节数组转换成16进制字符串
     * @param src
     * @return
     */
    private static String byteToHex(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
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