package club.gclmit.chaos.core.lang;

import java.util.Locale;

/**
 * 浏览器类型枚举
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/4/23 16:38
 * @since jdk11
 */
public enum Browsers {

	/**
	 * chrome
	 */
	Chrome,
	/**
	 * opera
	 */
	Opera,
	/**
	 * firefox
	 */
	Firefox,
	/**
	 * safari
	 */
	Safari,
	/**
	 * IE
	 */
	IE;

	public static String getCode(Browsers browsers) {
		return browsers.name().toLowerCase(Locale.ROOT);
	}
}
