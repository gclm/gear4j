package club.gclmit.chaos.web.filter.xss;

/**
 * xss 清理器
 *
 * @author L.cm
 */
public interface XssCleaner {

	/**
	 * 清理 html
	 *
	 * @param html html
	 * @return 清理后的数据
	 */
	String clean(String html);

}
