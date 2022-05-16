package club.gclmit.chaos.core.lang;

/**
 * $ 常量
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/5/16 18:20
 * @since jdk11
 */
public class $ {

	/**
	 * true 为 1
	 */
	public static final int TRUE = 1;

	/**
	 * true 为 0
	 */
	public static final int FALSE = 0;

	//----------------------------------Http --------------------------------------------

	/**
	 * get 请求
	 */
	public static final String GET = "GET";

	/**
	 * post  请求
	 */
	public static final String POST = "POST";

	/**
	 * update 更新请求
	 */
	public static final String PUT = "PUT";

	/**
	 * DELETE 删除请求
	 */
	public static final String DELETE = "DELETE";

	/**
	 * OPTIONS 请求
	 */
	public static final String OPTIONS = "OPTIONS";

	/**
	 * Http 魔法值
	 */
	public static final String UNKNOWN = "UNKNOWN";

	/**
	 * localhost 魔法值
	 */
	public static final String LOCALHOST = "0:0:0:0:0:0:0:1";
	/**
	 * 默认 host
	 */
	public static final String DEFAULT_HOST = "127.0.0.1";

	/**
	 * 上传内容类型
	 */
	public static final String UPLOAD_CONTENT_TYPE = "multipart/form-data";

	/**
	 * 默认请求内容类型
	 */
	public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
}
