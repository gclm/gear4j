package club.gclmit.chaos.core.util;

/**
 * TODO
 *
 * @author gclm
 * @since 12/21/2020 2:50 PM
 * @since 1.8
 */
public class UrlUtilsTest {

    public static void main(String[] args) {
        System.out.println(UrlUtils.hasUrlEncoded("13%2C14%2C15"));
        System.out.println(UrlUtils.hasUrlEncoded("13,14,15"));
    }
}
