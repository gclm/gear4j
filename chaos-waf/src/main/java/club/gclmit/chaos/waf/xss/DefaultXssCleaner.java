package club.gclmit.chaos.web.filter.xss;


/**
 * 默认的 xss 清理器
 *
 * @author L.cm
 */
public class DefaultXssCleaner implements XssCleaner {

	@Override
	public String clean(String html) {
		return XssUtils.clean(html);
	}

}
