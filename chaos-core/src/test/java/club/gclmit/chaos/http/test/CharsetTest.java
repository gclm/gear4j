package club.gclmit.chaos.http.test;

import club.gclmit.chaos.http.text.CharsetUtils;

import java.nio.charset.Charset;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/2 3:05 下午
 * @version: V1.0
 * @since 1.8
 */
public class CharsetTest {

    public static void main(String[] args) {
        Charset en = CharsetUtils.CHARSET_UTF_8;
        System.out.println(en.name());
        System.out.println(en.toString());
    }
}
