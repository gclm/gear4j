package club.gclmit.chaos.core.util;

import org.junit.Test;

/**
 * UrlUtils 测试工具类
 *
 * @author gclm
 * @since 12/21/2020 2:50 PM
 * @since 1.8
 */
public class UrlUtilsTest {

    @Test
    public void test1(){
        System.out.println(UrlUtils.hasUrlEncoded("13%2C14%2C15"));
        System.out.println(UrlUtils.hasUrlEncoded("13,14,15"));
    }
}
