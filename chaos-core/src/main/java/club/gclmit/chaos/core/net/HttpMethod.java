package club.gclmit.chaos.core.net;

/**
 * <p>
 * Http 请求类型
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/25 10:33 上午
 * @version: V1.0
 * @since 1.8
 */
public interface HttpMethod {

    /**
     * get 请求
     */
    public static final String GET  = "GET";

    /**
     *  post  请求
     */
    public static final String POST = "POST";

    /**
     *  update 更新请求
     */
    public static final String PUT  = "PUT";

    /**
     *  DELETE 删除请求
     */
    public static final String DELETE = "DELETE";

    /**
     *  OPTIONS 请求
     */
    public static final String OPTIONS  = "OPTIONS";
}
