package club.gclmit.chaos.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value = "分页消息封装", description = "分页消息封装")
public class PageResult extends Result {

    /**
     *  列表数量
     */
    @ApiModelProperty(value = "分页总数量")
    private Long count;

    public static PageResult result(ApiCode apiCode, Long count,Object data) {
        return new PageResult(apiCode.getCode(), apiCode.getMessage(),count,data);
    }

    public static PageResult ok(Integer code, String message,Object data) {
        return new PageResult(code, message, data);
    }

    public static PageResult ok(Long count,Object data) {
        return result(ApiCode.OK,count,data);
    }

    public static PageResult ok(Integer count,Object data) {
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
