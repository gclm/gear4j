package club.gclmit.chaos.core.helper;

import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
public class TimeUtils {

    private static final Map<Integer,String> WEEKS;

    private static final String TIME_ZONE_ID = "+8";

    static {
        Map<Integer,String> map = new HashMap<>(7);
        map.put(1,"星期一");
        map.put(2,"星期二");
        map.put(3,"星期三");
        map.put(4,"星期四");
        map.put(5,"星期五");
        map.put(6,"星期六");
        map.put(7,"星期七");
        WEEKS = Collections.unmodifiableMap(map);
    }

    /**
     *  时间戳 --> 星期 ？
     *
     * @author gclm
     * @param: timestamp
     * @date 2020/3/27 10:10 上午
     * @return: java.lang.String
     * @throws
     */
    public static String timestampToWeekName(Long timestamp){
        Assert.notNull(timestamp,"时间戳不能为空");
        Integer size = String.valueOf(timestamp).length();
        Assert.isTrue(10 == size || 13 == size,"时间戳格式不对");
        if (10 == size) {
            timestamp *= 1000;
        }
        LocalDateTime dateTime = timestampToLocalDateTime(timestamp);
        return WEEKS.get(dateTime.getDayOfWeek().getValue());
    }

    /**
     * 时间戳 --> String
     *
     * @author gclm
     * @param: timestamp
     * @param: format
     * @date 2020/3/27 12:03 下午
     * @return: java.lang.String
     * @throws
     */
    public static String timestampToString(Long timestamp, String format){
        LocalDateTime localDateTime = timestampToLocalDateTime(timestamp);
        return localDateTimeToString(localDateTime,format);
    }

    /**
     *  时间戳 --> LocalDateTime
     *
     * @author gclm
     * @param: timestamp
     * @date 2020/3/27 10:22 上午
     * @return: java.time.LocalDateTime
     * @throws
     */
    public static LocalDateTime timestampToLocalDateTime(Long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.of(TIME_ZONE_ID));
    }

    /**
     * LocalDateTime --> 星期 ？
     *
     * @author gclm
     * @param: dateTime
     * @date 2020/3/27 10:10 上午
     * @return: java.lang.String
     * @throws
     */
    public static String localDateTimeToWeekName(LocalDateTime dateTime){
        Assert.notNull(dateTime,"LocalDateTime 不能为空");
        return WEEKS.get(dateTime.getDayOfWeek().getValue());
    }

    /**
     *  LocalDateTime --> 自定义日期格式
     *
     * @author gclm
     * @param: localDateTime
     * @param: format
     * @date 2020/3/27 10:22 上午
     * @return: java.time.LocalDateTime
     * @throws
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     *  解析自定义日期 --> LocalDateTime
     *
     * @author gclm
     * @param: localDateTime
     * @param: format
     * @date 2020/3/27 10:22 上午
     * @return: java.time.LocalDateTime
     * @throws
     */
    public static LocalDateTime stringToLocalDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     *  获取秒时间戳
     *
     * @author gclm
     * @date 2020/1/19 11:32 上午
     * @return: java.lang.Long
     * @throws
     */
    public static Long getSecondTimestamp(){
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of(TIME_ZONE_ID));
    }

    /**
     *  获取秒时间戳
     *
     * @author gclm
     * @param: dateTime
     * @date 2020/3/28 11:16 上午
     * @return: java.lang.Long
     * @throws
     */
    public static Long getSecondTimestamp(LocalDateTime dateTime){
        return dateTime.toEpochSecond(ZoneOffset.of(TIME_ZONE_ID));
    }

    /**
     *  获取耗秒时间戳
     *
     * @author gclm
     * @date 2020/1/19 11:32 上午
     * @return: java.lang.Long
     * @throws
     */
    public static Long getMilliTimestamp(){
        return LocalDateTime.now().toInstant(ZoneOffset.of(TIME_ZONE_ID)).toEpochMilli();
    }

    /**
     *  获取耗秒时间戳
     *
     * @author gclm
     * @param: dateTime
     * @date 2020/3/28 11:15 上午
     * @return: java.lang.Long
     * @throws
     */
    public static Long getMilliTimestamp(LocalDateTime dateTime){
        return dateTime.toInstant(ZoneOffset.of(TIME_ZONE_ID)).toEpochMilli();
    }
}
