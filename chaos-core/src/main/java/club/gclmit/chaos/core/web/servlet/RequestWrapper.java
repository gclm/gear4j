package club.gclmit.chaos.core.web.servlet;

import club.gclmit.chaos.core.lang.text.Charsets;
import club.gclmit.chaos.core.lang.text.StringUtils;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>
 * 自定义 HttpServletRequestWrapper
 * 设置缓存快照
 * </p>
 * @author gclm
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * 设置默认编码格式为 UTF-8
     */
    private static final String DEFAULT_CHARSET = Charsets.UTF_8;

    /**
     * Request Body
     */
    private final String body;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IOException if the request is null
     */
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        ServletInputStream stream = request.getInputStream();
        this.body = StringUtils.toString(stream, DEFAULT_CHARSET);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(DEFAULT_CHARSET));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return body;
    }
}
