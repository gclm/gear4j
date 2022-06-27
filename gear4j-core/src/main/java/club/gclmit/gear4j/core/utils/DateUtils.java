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

	public static long current() {
		return SystemClock.now();
	}
}
