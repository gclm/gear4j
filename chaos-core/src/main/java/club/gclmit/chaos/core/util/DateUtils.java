package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.lang.Assert;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 时间工具类，
 * 功能：
 * - 用户获取时间戳。
 * 单位：
 * - 秒
 * - 毫秒
 * </p>
 *
 * @author gclm
 */
public class DateUtils {

    private static final Map<Integer, String> WEEKS;

    /**
     * 默认时区 东八区
     */
    public static final String TIME_ZONE_ID = "+8";

    /**
     * 时间戳长度 10
     */
    public static final Integer TIMESTAMP_LENGTH_10 = 10;
    /**
     * 时间戳长度 13
     */
    public static final Integer TIMESTAMP_LENGTH_13 = 13;

    static {
        Map<Integer, String> map = new HashMap<>(7);
        map.put(1, "星期一");
        map.put(2, "星期二");
        map.put(3, "星期三");
        map.put(4, "星期四");
        map.put(5, "星期五");
        map.put(6, "星期六");
        map.put(7, "星期七");
        WEEKS = Collections.unmodifiableMap(map);
    }

    /**
     * 获取当前日期的年
     *
     * @author gclm
     * @return: java.lang.Integer
     */
    public static Integer thisYear() {
        return LocalDate.now().getYear();
    }

    /**
     * 判断当前年份是否为闰年
     *
     * @author gclm
     * @param year 判断年份
     * @return boolean
     */
    public static boolean isLeapYear(long year) {
        return ((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0);
    }

    /**
     * 时间戳 To 星期 ？
     *
     * @author gclm
     * @param timestamp  时间戳
     * @return java.lang.String
     */
    public static String timestampToWeekName(Long timestamp) {
        Assert.notNull(timestamp, "时间戳不能为空");
        Integer size = String.valueOf(timestamp).length();
        Assert.isTrue(TIMESTAMP_LENGTH_10.equals(size) || TIMESTAMP_LENGTH_13.equals(size), "时间戳格式不对");
        timestamp = (TIMESTAMP_LENGTH_10.equals(size)) ? timestamp *= 1000 : timestamp;
        LocalDateTime dateTime = timestampToLocalDateTime(timestamp);
        return WEEKS.get(dateTime.getDayOfWeek().getValue());
    }

    /**
     * 时间戳 To String
     *
     * @author gclm
     * @param timestamp  时间戳
     * @param format     日期格式
     * @return java.lang.String
     */
    public static String timestampToString(Long timestamp, String format) {
        LocalDateTime localDateTime = timestampToLocalDateTime(timestamp);
        return localDateTimeToString(localDateTime, format);
    }

    /**
     * 时间戳 To LocalDateTime
     *
     * @author gclm
     * @param timestamp  时间戳
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime timestampToLocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.of(TIME_ZONE_ID));
    }

    /**
     * LocalDateTime To 星期 ？
     *
     * @author gclm
     * @param dateTime LocalDateTime
     * @return java.lang.String
     */
    public static String localDateTimeToWeekName(LocalDateTime dateTime) {
        Assert.notNull(dateTime, "LocalDateTime 不能为空");
        return WEEKS.get(dateTime.getDayOfWeek().getValue());
    }

    /**
     * LocalDateTime To 自定义日期格式
     *
     * @author gclm
     * @param localDateTime  LocalDateTime
     * @param format         日期格式
     * @return java.time.LocalDateTime
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     * 解析自定义日期 To LocalDateTime
     *
     * @author gclm
     * @param time  LocalDateTime
     * @param format         日期格式
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * 获取秒时间戳
     *
     * @author gclm
     * @return java.lang.Long
     */
    public static Long getSecondTimestamp() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of(TIME_ZONE_ID));
    }

    /**
     * 获取秒时间戳
     *
     * @author gclm
     * @param dateTime LocalDateTime
     * @return java.lang.Long
     */
    public static Long getSecondTimestamp(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.of(TIME_ZONE_ID));
    }

    /**
     * 获取耗秒时间戳
     *
     * @author gclm
     * @return java.lang.Long
     */
    public static Long getMilliTimestamp() {
        return LocalDateTime.now().toInstant(ZoneOffset.of(TIME_ZONE_ID)).toEpochMilli();
    }

    /**
     * 获取耗秒时间戳
     *
     * @author gclm
     * @param dateTime LocalDateTime
     * @return java.lang.Long
     */
    public static Long getMilliTimestamp(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.of(TIME_ZONE_ID)).toEpochMilli();
    }
}
