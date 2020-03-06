package club.gclmit.chaos.web.response;

/**
 * <p>
 *   对PageResult 的封装，方便集成 Layui
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/27 9:19 下午
 * @version: V1.0
 * @since 1.8
 */
public class PageResult extends Result {

    /**
     *  列表数量
     */
    private Long count;

    public static PageResult result(StatusCode statusCode, Long count,Object data) {
        return new PageResult(statusCode.getCode(), statusCode.getMessage(),count,data);
    }

    public static PageResult ok(Integer code, String message,Object data) {
        return new PageResult(code, message, data);
    }

    public static PageResult ok(Long count,Object data) {
        return result(StatusCode.OK,count,data);
    }

    public PageResult(Integer code, String message, Long count ,Object data) {
        super(code, message, data);
        this.count = count;
    }

    public PageResult(Integer code, String message, Object data) {
        super(code, message, data);
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
