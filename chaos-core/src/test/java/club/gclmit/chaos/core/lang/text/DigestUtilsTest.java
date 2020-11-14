package club.gclmit.chaos.core.lang.text;

import club.gclmit.chaos.core.io.FileUtils;
import cn.hutool.crypto.SecureUtil;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 加密算法测试
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/23 10:29 上午
 * @version: V1.0
 * @since 1.8
 */
public class DigestUtilsTest {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/gclm/Projects/java/middleware/chaos/chaos-core/src/test/resources/test1.jpg");
        File file1 = new File("/Users/gclm/Projects/java/middleware/chaos/chaos-core/src/test/resources/test1-1.jpeg");
        File file2 = new File("/Users/gclm/Projects/java/middleware/chaos/chaos-core/src/test/resources/test1-3.pdf");

        System.out.println("file:  " + SecureUtil.md5(file));
        System.out.println("file1: " + SecureUtil.md5(file1));
        System.out.println("file2: " + SecureUtil.md5(file2));

        System.out.println(FileUtils.checkFile(file,file1));
    }
}
