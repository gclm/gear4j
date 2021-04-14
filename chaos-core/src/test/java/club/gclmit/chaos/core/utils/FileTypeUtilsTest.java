package club.gclmit.chaos.core.utils;

import club.gclmit.chaos.core.io.FileTypeUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * <p>
 * FileUtils 测试工具类
 * </p>
 *
 * @author gclm
 * @version V1.0
 * @date 2019/11/6 23:56
 */
public class FileTypeUtilsTest {

    public static String path = "";

    @Before
    public void init() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:test");
        path = file.getAbsolutePath() + File.separator;
    }

    /**
     * 获取文件类型测试
     */
    @Test
    public void judgeFileTypeTest() throws FileNotFoundException {
        System.out.println(FileTypeUtils.getSuffixByMagic(new File(path, "test1.jpg")));
        System.out.println(FileTypeUtils.getSuffixByMagic(new File(path, "test1-1.jpeg")));
        System.out.println(FileTypeUtils.getSuffixByMagic(new File(path, "test1-2.png")));
        System.out.println(FileTypeUtils.getSuffixByMagic(new File(path, "test1-3.pdf")));
        System.out.println("============================");
    }

    @Test
    public void getContentTypeTest() {
        System.out.println(FileTypeUtils.getMimeType(new File(path, "test1.jpg")));
        System.out.println(FileTypeUtils.getMimeType(new File(path, "test1-1.jpeg")));
        System.out.println(FileTypeUtils.getMimeType(new File(path, "test1-2.png")));
        System.out.println(FileTypeUtils.getMimeType(new File(path, "test1-3.pdf")));
    }
}
