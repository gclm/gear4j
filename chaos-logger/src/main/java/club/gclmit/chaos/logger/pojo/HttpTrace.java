package club.gclmit.chaos.logger.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
    private int httpStatusCode;

    /**
     *  请求耗时（秒）
     */
    @ApiModelProperty(value = "请求耗时（秒）")
    private long consumingTime;

    /**
     *  接口返回时间
     */
    @ApiModelProperty(value = "接口返回时间")
    private Long responseTime;

    /**
     * requestBody
     */
    @ApiModelProperty(value = "请求参数内容")
    private String request;

    /**
     *  responseBody
     */
    @ApiModelProperty(value = "接口返回数据")
    private String response;

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

    public HttpTrace() {
    }

    public HttpTrace(String clientIp, String uri, String contentType, String method, String sessionId, Long requestTime,int httpStatusCode) {
        this.clientIp = clientIp;
        this.uri = uri;
        this.contentType = contentType;
        this.method = method;
        this.sessionId = sessionId;
        this.requestTime = requestTime;
        this.httpStatusCode = httpStatusCode;
    }

    public HttpTrace(String clientIp, String uri, String contentType, String method, String sessionId, Long requestTime, String requestHeader, String userAgent) {
        this.clientIp = clientIp;
        this.uri = uri;
        this.contentType = contentType;
        this.method = method;
        this.sessionId = sessionId;
        this.requestTime = requestTime;
        this.requestHeader = requestHeader;
        this.userAgent = userAgent;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public long getConsumingTime() {
        return consumingTime;
    }

    public void setConsumingTime(long consumingTime) {
        this.consumingTime = consumingTime;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "HttpTrace{" +
                "clientIp='" + clientIp + '\'' +
                ", uri='" + uri + '\'' +
                ", contentType='" + contentType + '\'' +
                ", method='" + method + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", requestTime=" + requestTime +
                ", httpStatusCode=" + httpStatusCode +
                ", consumingTime=" + consumingTime +
                ", responseTime=" + responseTime +
                ", request='" + request + '\'' +
                ", response='" + response + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", responseHeader='" + responseHeader + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}


