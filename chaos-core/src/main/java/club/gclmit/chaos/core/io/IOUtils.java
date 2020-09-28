package club.gclmit.chaos.core.io;

import club.gclmit.chaos.core.util.ExceptionUtils;
import club.gclmit.chaos.core.text.CharsetUtils;
import com.google.common.io.CharStreams;
import org.springframework.lang.Nullable;

import java.io.*;
import java.nio.charset.Charset;

/**
 * <p>
 * IO 流工具类
 * </p>
 *
 * @author gclm
 */
public class IOUtils extends org.springframework.util.StreamUtils {

    /**
     * closeQuietly
     *
     * @param closeable 自动关闭
     */
    public static void closeQuietly(@Nullable Closeable closeable) {
        if (closeable == null) {
            return;
        }
        if (closeable instanceof Flushable) {
            try {
                ((Flushable) closeable).flush();
            } catch (IOException ignored) {
                // ignore
            }
        }
        try {
            closeable.close();
        } catch (IOException ignored) {
            // ignore
        }
    }

    /**
     * InputStream to String utf-8
     *
     * @param input the <code>InputStream</code> to read from
     * @return the requested String
     */
    public static String copy(InputStream input) {
        return copy(input, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * InputStream to String
     *
     * @param input   输入流
     * @param charset 编码格式
     * @return 字符串
     */
    public static String copy(@Nullable InputStream input, Charset charset) {
        try {
            return copyToString(input, charset);
        } catch (IOException e) {
            throw ExceptionUtils.unchecked(e);
        } finally {
            closeQuietly(input);
        }
    }

    /**
     * Reader to String
     *
     * @param input   Reader
     * @return 字符串
     */
    public static String copy(Reader input) throws IOException {
        return CharStreams.toString(input);
    }

    /**
     * Writes chars from a <code>String</code> to bytes on an
     * <code>OutputStream</code> using the specified character encoding.
     * <p>
     * This method uses {@link String#getBytes(String)}.
     * </p>
     * @param data     the <code>String</code> to write, null ignored
     * @param output   the <code>OutputStream</code> to write to
     * @param encoding the encoding to use, null means platform default
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     */
    public static void write(@Nullable final String data, final OutputStream output, final Charset encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(encoding));
        }
    }
}
