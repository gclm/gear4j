package club.gclmit.chaos.core.beans;

import org.springframework.format.support.DefaultFormattingConversionService;

/**
 * 通过单例模式获取 DefaultFormattingConversionService
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since jdk11
 */
public class ChaosConversionService extends DefaultFormattingConversionService {

	private static class ChaosConversionServiceHandler {
		static ChaosConversionService instance = new ChaosConversionService();
	}

	/**
	 * 实例化单例ChaosConversionService
	 *
	 * @return {@link ChaosConversionService}
	 */
	public static ChaosConversionService getInstance() {
		return ChaosConversionServiceHandler.instance;
	}
}
