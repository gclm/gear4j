package club.gclmit.chaos.helper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <p>
 * 时间工具类，
 * 功能：
 *  - 用户获取时间戳。
 * 单位：
 *  - 秒
 *  - 毫秒
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/19 11:27 上午
 * @version: V1.0
 * @since 1.8
 */
public class TimeHelper {

    /**
     *  获取秒时间戳
     *
     * @author gclm
     * @date 2020/1/19 11:32 上午
     * @return: java.lang.Long
     * @throws
     */
    public static Long toSeconds(){
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     *  获取耗秒时间戳
     *
     * @author gclm
     * @date 2020/1/19 11:32 上午
     * @return: java.lang.Long
     * @throws
     */
    public static Long toMillis(){
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
