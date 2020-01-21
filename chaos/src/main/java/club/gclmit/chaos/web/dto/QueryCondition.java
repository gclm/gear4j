package club.gclmit.chaos.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 查询条件封装
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/20 5:55 下午
 * @version: V1.0
 * @since 1.8
 */
@ApiModel(value = "查询条件", description = "查询条件")
public class QueryCondition {

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码",required = false,example = "1")
    private Long page = 1L;

    /**
     * 每页数据条数
     */
    @ApiModelProperty(value = "每页数据条数",required = false,example = "15")
    private Long limit = 15L;

    /**
     * 排序
     * 0: 正序
     * 1: 倒序
     * 3: 乱序
     */
    @ApiModelProperty(value = "排序",required = false,example = "0")
    private int sort;

    /**
     * json 类型的条件参数
     */
    @ApiModelProperty(value = "JSON 类型的条件",required = false,example = "{\"id\":111111}")
    private Object condition;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }
}
