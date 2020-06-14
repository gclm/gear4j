package club.gclmit.chaos.logger.model;

/**
 * Build设计模式
 *
 * @author gclm
 */
public class HttpTraceBuilder {

    private HttpTrace httpTrace;

    public HttpTraceBuilder() {
        httpTrace = new HttpTrace();
    }

    public HttpTraceBuilder id(Long id) {
        this.httpTrace.setId(id);
        return this;
    }

    public HttpTraceBuilder clientIp(String clientIp) {
        this.httpTrace.setClientIp(clientIp);
        return this;
    }

    public HttpTraceBuilder uri(String uri) {
        this.httpTrace.setUri(uri);
        return this;
    }

    public HttpTraceBuilder contentType(String contentType) {
        this.httpTrace.setContentType(contentType);
        return this;
    }

    public HttpTraceBuilder method(String method) {
        this.httpTrace.setMethod(method);
        return this;
    }

    public HttpTraceBuilder sessionId(String sessionId) {
        this.httpTrace.setSessionId(sessionId);
        return this;
    }

    public HttpTraceBuilder requestTime(Long requestTime) {
        this.httpTrace.setRequestTime(requestTime);
        return this;
    }

    public HttpTraceBuilder httpCode(int httpCode) {
        this.httpTrace.setHttpCode(httpCode);
        return this;
    }

    public HttpTraceBuilder consumingTime(Long consumingTime) {
        this.httpTrace.setConsumingTime(consumingTime);
        return this;
    }

    public HttpTraceBuilder responseTime(Long responseTime) {
        this.httpTrace.setResponseTime(responseTime);
        return this;
    }

    public HttpTraceBuilder requestBody(String requestBody) {
        this.httpTrace.setRequestBody(requestBody);
        return this;
    }

    public HttpTraceBuilder responseBody(String responseBody) {
        this.httpTrace.setResponseBody(responseBody);
        return this;
    }

    public HttpTraceBuilder requestHeader(String requestHeader) {
        this.httpTrace.setRequestHeader(requestHeader);
        return this;
    }

    public HttpTraceBuilder responseHeader(String responseHeader) {
        this.httpTrace.setResponseHeader(responseHeader);
        return this;
    }

    public HttpTraceBuilder userAgent(String userAgent) {
        this.httpTrace.setUserAgent(userAgent);
        return this;
    }

    public HttpTrace build() {
        return this.httpTrace;
    }
}
