package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * FileUtils 测试工具类
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/6 23:56
 * @version: V1.0
 */
public class FileUtilsTest {

    public static void main(String[] args) throws IOException {
        judgeFileTypeTest();
        System.out.println("=====================");
        getContentTypeTest();
    }

     /**
      * 获取文件类型测试
      */
    private static void judgeFileTypeTest(){
        String path = getPath();
        System.out.println(FileUtils.judgeFileType(new File(path,"test1.jpg")));
        System.out.println(FileUtils.judgeFileType(new File(path,"test1-1.jpeg")));
        System.out.println(FileUtils.judgeFileType(new File(path,"test1-2.png")));
        System.out.println(FileUtils.judgeFileType(new File(path,"test1-3.pdf")));
    }

    private static void getContentTypeTest(){
        String path = getPath();
        System.out.println(FileUtils.getMimeType(new File(path,"test1.jpg")));
        System.out.println(FileUtils.getMimeType(new File(path,"test1-1.jpeg")));
        System.out.println(FileUtils.getMimeType(new File(path,"test1-2.png")));
        System.out.println(FileUtils.getMimeType(new File(path,"test1-3.pdf")));
    }


    private static String getPath(){
        return System.getProperty("user.dir") +
                File.separator + "chaos-core" +
                File.separator + "src" +
                File.separator + "test" +
                File.separator + "resources" + File.separator;
    }

}
