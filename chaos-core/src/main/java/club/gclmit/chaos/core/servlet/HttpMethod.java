package club.gclmit.chaos.core.servlet;

/**
 * <p>
 * Http 请求类型
 * </p>
 *
 * @author gclm
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
