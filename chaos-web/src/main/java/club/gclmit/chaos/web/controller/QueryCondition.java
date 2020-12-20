package club.gclmit.chaos.web.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 查询条件封装
 * </p>
 *
 * @author gclm
 */
@Data
@ApiModel(value = "查询条件", description = "查询条件")
public class QueryCondition implements Serializable {

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

    @ApiModelProperty(value = "json类型的查询参数")
    private String data;

}