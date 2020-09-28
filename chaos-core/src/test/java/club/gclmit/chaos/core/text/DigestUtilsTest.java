package club.gclmit.chaos.core.text;

import club.gclmit.chaos.core.text.DigestUtils;

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
        File file = new File("/Users/gclm/Pictures/27618687.jpeg");
        File file1 = new File("/Users/gclm/Pictures/avatar.jpg");
        File file2 = new File("/Users/gclm/Pictures/截图/Xnip2020-04-30_16-54-26.jpg");

//        System.out.println("file:  " + DigestUtils.md5(file));
//        System.out.println("file1: " + DigestUtils.md5(file1));
//        System.out.println("file2: " + DigestUtils.md5(file2));
    }
}
