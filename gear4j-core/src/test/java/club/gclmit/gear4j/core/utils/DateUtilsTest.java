package club.gclmit.gear4j.core.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * DateUtils 测试
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/3 16:23
 * @since jdk11
 */
@DisplayName("DateUtils 测试")
public class DateUtilsTest {

    @Test
    public void getTime() {
        System.out.println(DateUtils.getTime());
    }

    @Test
    public void getTimeSeconds() {
        System.out.println(DateUtils.getTimeSeconds());
    }
}
