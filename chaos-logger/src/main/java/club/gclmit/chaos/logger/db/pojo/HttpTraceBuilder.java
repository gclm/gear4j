package club.gclmit.chaos.logger.db.pojo;


/**
 * <p>
 *  构造器模式
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/29 11:41 下午
 * @version: V1.0
 * @since 1.8
 */
public class HttpTraceBuilder {

    private HttpTrace httpTrace;

    public HttpTraceBuilder() {
        this.httpTrace = new HttpTrace();
    }

    /**
     * 客户端IP
     */
    public HttpTraceBuilder clientIp(String clientIp){
        this.httpTrace.setClientIp(clientIp);
        return this;
    }

    /**
     * 客户端请求的路径
     */
    public HttpTraceBuilder uri(String uri){
        this.httpTrace.setUri(uri);
        return this;
    }

    /**
     * 客户端请求方式
     */
    public HttpTraceBuilder contentType(String contentType){
        this.httpTrace.setContentType(contentType);
        return this;
    }

    /**
     *  请求方法类型: restful 风格
     */
    public HttpTraceBuilder method(String method){
        this.httpTrace.setMethod(method);
        return this;
    }

    /**
     *  请求接口 唯一 session 标识
     */
    public HttpTraceBuilder sessionId(String sessionId){
        this.httpTrace.setSessionId(sessionId);
        return this;
    }

    /**
     *  请求时间戳（秒）
     */
    public HttpTraceBuilder requestTime(Long requestTime){
        this.httpTrace.setRequestTime(requestTime);
        return this;
    }

    /**
     * 请求的 httpStatusCode 状态码
     */
    public HttpTraceBuilder httpCode(int httpCode){
        this.httpTrace.setHttpCode(httpCode);
        return this;
    }

    /**
     *  请求耗时（秒）
     */
    public HttpTraceBuilder consumingTime(Long consumingTime){
        this.httpTrace.setConsumingTime(consumingTime);
        return this;
    }

    /**
     *  接口返回时间
     */
    public HttpTraceBuilder responseTime(Long responseTime){
        this.httpTrace.setResponseTime(responseTime);
        return this;
    }

    /**
     * requestBody
     */
    public HttpTraceBuilder requestBody(String requestBody){
        this.httpTrace.setRequestBody(requestBody);
        return this;
    }

    /**
     *  responseBody
     */
    public HttpTraceBuilder responseBody(String responseBody){
        this.httpTrace.setResponseBody(responseBody);
        return this;
    }

    /**
     *  request 请求头
     */
    public HttpTraceBuilder requestHeader(String requestHeader){
        this.httpTrace.setRequestHeader(requestHeader);
        return this;
    }

    /**
     *  response 响应头
     */
    public HttpTraceBuilder responseHeader(String responseHeader){
        this.httpTrace.setResponseHeader(responseHeader);
        return this;
    }

    /**
     * 用户代理
     */
    public HttpTraceBuilder userAgent(String userAgent){
        this.httpTrace.setUserAgent(userAgent);
        return this;
    }

    /**
     * 返回HttpTrace
     */
    public HttpTrace build() {
        return this.httpTrace;
    }

}
