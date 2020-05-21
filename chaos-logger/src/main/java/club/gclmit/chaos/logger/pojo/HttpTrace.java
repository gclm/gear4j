package club.gclmit.chaos.logger.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 日志组件实体类
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/9 3:21 下午
 * @version: V1.0
 * @since 1.8
 */
@Data
@Builder
@TableName("chaos_trace_info")
@ApiModel(value="HttpTrace 对象", description="")
public class HttpTrace implements Serializable {

    /**
     *  主键 编号
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     *  客户端请求ip
     */
    @ApiModelProperty(value = "客户端请求ip")
    private String clientIp;

    /**
     * 客户端请求的路径
     */
    @ApiModelProperty(value = "客户端请求的路径")
    private String uri;

    /**
     * 客户端请求方式
     */
    @ApiModelProperty(value = "客户端请求方式")
    private String contentType;

    /**
     *  请求方法类型: restful 风格
     */
    @ApiModelProperty(value = "请求方法类型")
    private String method;

    /**
     *  请求接口 唯一 session 标识
     */
    @ApiModelProperty(value = "唯一 session 标识")
    private String sessionId;

    /**
     *  请求时间戳（秒）
     */
    @ApiModelProperty(value = "时间戳（秒）")
    private Long requestTime;

    /**
     * 请求的 httpStatusCode 状态码
     */
    @ApiModelProperty(value = "http 状态码")
    private int httpCode;

    /**
     *  请求耗时（秒）
     */
    @ApiModelProperty(value = "请求耗时（秒）")
    private Long consumingTime;

    /**
     *  接口返回时间
     */
    @ApiModelProperty(value = "接口返回时间")
    private Long responseTime;

    /**
     * requestBody
     */
    @ApiModelProperty(value = "请求参数内容")
    private String requestBody;

    /**
     *  responseBody
     */
    @ApiModelProperty(value = "接口返回数据")
    private String responseBody;

    /**
     *  request 请求头
     */
    @ApiModelProperty(value = "request 请求头 ")
    private String requestHeader;

    /**
     *  response 响应头
     */
    @ApiModelProperty(value = "response 响应头")
    private String responseHeader;

    /**
     * 用户代理
     */
    @ApiModelProperty(value = "用户代理")
    private String userAgent;

}


