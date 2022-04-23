package club.gclmit.chaos.core.utils;

import club.gclmit.chaos.core.lang.Browsers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * UserAgent 测试工具类
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @version Version 1.0.0
 * @since 2021/9/26 2:46 下午
 * @since jdk11
 */
public class UserAgentUtilsTest {

	@Test
	@DisplayName("获取指定浏览器UserAgent")
	public void test1() {
		String agent = UserAgentUtils.getUserAgent(Browsers.Chrome);
		System.out.println(agent);
	}

	@Test
	@DisplayName("随机获取浏览器UserAgent")
	public void test2() {
		String agent = UserAgentUtils.getRandomUserAgent();
		System.out.println(agent);
	}
}
