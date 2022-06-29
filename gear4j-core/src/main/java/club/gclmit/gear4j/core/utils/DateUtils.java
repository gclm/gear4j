package club.gclmit.gear4j.core.utils;

import club.gclmit.gear4j.core.lang.SystemClock;
import cn.hutool.core.date.DateUtil;

/**
 * 时间工具类优化
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/26 23:44
 * @since jdk11
 */
public class DateUtils extends DateUtil {

    /**
     * 当前时间的时间戳（毫秒）
     *
     * @return 当前时间毫秒数
     */
    public static long getTime() {
        return SystemClock.now();
    }

    /**
     * 当前时间的时间戳（秒）
     *
     * @return 当前时间秒数
     */
    public static long getTimeSeconds() {
        return SystemClock.now() / 1000;
    }
}
