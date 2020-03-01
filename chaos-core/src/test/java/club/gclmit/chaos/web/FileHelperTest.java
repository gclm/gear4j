package club.gclmit.chaos.web;

import club.gclmit.chaos.core.helper.file.FileHelper;

import java.io.IOException;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/6 23:56
 * @version: V1.0
 */
public class FileHelperTest {

    public static void main(String[] args) throws IOException {
        getFileTypeTest();
    }

     /**
      * 获取文件类型测试
      */
    private static void getFileTypeTest() throws IOException {
        String path = System.getProperty("user.dir");
        String filePath = new StringBuilder(path).append("//src//test//resources//").toString();

        System.out.println(FileHelper.getFileType(filePath + "test.png").getKey());
        System.out.println(FileHelper.getFileType(filePath + "test.pdf").getKey());
    }

}
