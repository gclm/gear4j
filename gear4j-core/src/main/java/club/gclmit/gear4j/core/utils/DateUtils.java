package club.gclmit.gear4j.core.utils;

import club.gclmit.gear4j.core.lang.SystemClock;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间工具类优化
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/26 23:44
 * @since jdk11
 */
public class DateUtils extends LocalDateTimeUtil {

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

	/**
	 * 判断时间大小
	 *
	 * @return true没过期，false过期
	 */
	public static boolean isExpired(LocalDateTime startDate, LocalDateTime endDate) {
		return between(startDate, endDate).toMillis() > 0;
	}

	/**
	 * to Date
	 *
	 * @param localDateTime 当地日期时间
	 * @return {@link Date}
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static void main(String[] args) {
		LocalDateTime dateTime = now();
		System.out.println(isExpired(dateTime, dateTime.plusHours(2)));
		System.out.println(isExpired(dateTime, dateTime.plusHours(-2)));
	}
}
