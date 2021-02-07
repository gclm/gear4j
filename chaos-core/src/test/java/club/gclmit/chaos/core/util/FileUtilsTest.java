package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * <p>
 * FileUtils 测试工具类
 * </p>
 *
 * @author gclm
 * @date 2019/11/6 23:56
 * @version V1.0
 */
public class FileUtilsTest {

    public static String path = "";

    @Before
    public void init(){
        path = System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "test" +
                File.separator + "resources" + File.separator;
    }

     /**
      * 获取文件类型测试
      */
     @Test
     public void judgeFileTypeTest(){
        System.out.println(FileUtils.judgeFileType(new File(path,"test1.jpg")));
        System.out.println(FileUtils.judgeFileType(new File(path,"test1-1.jpeg")));
        System.out.println(FileUtils.judgeFileType(new File(path,"test1-2.png")));
        System.out.println(FileUtils.judgeFileType(new File(path,"test1-3.pdf")));
        System.out.println("============================");
    }

    @Test
    public void getContentTypeTest(){
        System.out.println(FileUtils.getMimeType(new File(path,"test1.jpg")));
        System.out.println(FileUtils.getMimeType(new File(path,"test1-1.jpeg")));
        System.out.println(FileUtils.getMimeType(new File(path,"test1-2.png")));
        System.out.println(FileUtils.getMimeType(new File(path,"test1-3.pdf")));
    }

}
