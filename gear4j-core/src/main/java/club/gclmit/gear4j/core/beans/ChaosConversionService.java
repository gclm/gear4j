package club.gclmit.gear4j.core.beans;

import org.springframework.format.support.DefaultFormattingConversionService;

/**
 * 通过单例模式获取 DefaultFormattingConversionService
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since jdk11
 */
public class ChaosConversionService extends DefaultFormattingConversionService {

	/**
	 * 实例化单例ChaosConversionService
	 *
	 * @return {@link ChaosConversionService}
	 */
	public static ChaosConversionService getInstance() {
		return ChaosConversionServiceHandler.instance;
	}

	private static class ChaosConversionServiceHandler {
		static ChaosConversionService instance = new ChaosConversionService();
	}
}
