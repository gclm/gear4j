package club.gclmit.chaos.web.result;

/**
 * <p>
 *   对PageResult 的封装，方便集成 Layui
 * </p>
 *
 * @author gclm
 */
public class PageResult extends Result {

    /**
     *  列表数量
     */
    private Long count;

    public static PageResult result(ApiCode apiCode, Long count, Object data) {
        return new PageResult(apiCode.getCode(), apiCode.getMessage(),count,data);
    }

    public static PageResult ok(Integer code, String message, Object data) {
        return new PageResult(code, message, data);
    }

    public static PageResult ok(Long count, Object data) {
        return result(ApiCode.OK,count,data);
    }

    public static PageResult ok(Integer count, Object data) {
        return ok(Long.valueOf(count),data);
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
