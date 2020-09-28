package club.gclmit.chaos.http.servlet;

import club.gclmit.chaos.http.text.CharsetUtils;
import club.gclmit.chaos.http.text.StringUtils;
import org.springframework.lang.Nullable;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * <p>
 * HttpServletResponse 缓存
 * </p>
 *
 * @author gclm
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    @Nullable
    private ServletOutputStream outputStream;

    @Nullable
    private PrintWriter writer;

    private ResponseServletOutputStream stream;

    /**
     * Create a new ContentCachingResponseWrapper for the given servlet response.
     *
     * @param response the original servlet response
     */
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (outputStream == null) {
            outputStream = getResponse().getOutputStream();
            stream = new ResponseServletOutputStream(outputStream);
        }

        return stream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            stream = new ResponseServletOutputStream(getResponse().getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(stream, getCharset()), true);
        }
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            stream.flush();
        }
    }

    public String getBody() {
        byte[] bytes = new byte[0];
        if (stream != null) {
            bytes = stream.getCopy();
        }
        return StringUtils.toString(bytes, getCharset());
    }

    private String getCharset() {
//        return getCharacterEncoding() != null ? getCharacterEncoding() : CharsetUtils.UTF_8;
        return CharsetUtils.UTF_8;
    }

    private class ResponseServletOutputStream extends ServletOutputStream {

        /**
         * Output Stream
         */
        private OutputStream outputStream;
        /**
         * Copy Byte Array Output Stream
         */
        private ByteArrayOutputStream copy;

        public ResponseServletOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
            this.copy = new ByteArrayOutputStream(1024);
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            copy.write(b);
        }

        public byte[] getCopy() {
            return copy.toByteArray();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}
