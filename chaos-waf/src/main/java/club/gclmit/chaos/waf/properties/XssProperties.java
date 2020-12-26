package club.gclmit.chaos.waf.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Xss配置类
 *
 * @author gclm
 */
@Getter
@Setter
public class XssProperties {

	/**
	 * 开启xss
	 */
	private boolean enabled = true;

	/**
	 * 拦截的路由，默认为空
	 */
	private List<String> pathPatterns = new ArrayList<>();

	/**
	 * 放行的规则，默认为空
	 */
	private List<String> excludePatterns = new ArrayList<>();

}
