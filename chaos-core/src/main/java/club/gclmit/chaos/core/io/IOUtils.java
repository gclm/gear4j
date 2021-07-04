package club.gclmit.chaos.core.io;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.utils.CharsetUtils;
import club.gclmit.chaos.core.utils.ExceptionUtils;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.IoUtil;
import com.google.common.io.Files;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * IO 流工具类
 * </p>
 *
 * @author gclm
 */
@UtilityClass
public class IOUtils extends org.springframework.util.StreamUtils {

    /**
     * IO流是否为空
     *
     * @param stream IO流
     * @return 是否为空
     */
    public static boolean isEmpty(InputStream stream) {
        try {
            return stream == null || stream.available() == 0;
        } catch (IOException e) {
            throw ExceptionUtils.wrapRuntime(e);
        }
    }

    /**
     * IO流是否为空
     *
     * @param stream IO流
     * @return 是否为空
     */
    public static boolean isEmpty(OutputStream stream) {
        return stream == null;
    }

    /**
     * IO流是否为空
     *
     * @param stream IO流
     * @return 是否为空
     */
    public static boolean isNotEmpty(InputStream stream) {
        return !isEmpty(stream);
    }

    /**
     * IO流是否为空
     *
     * @param stream IO流
     * @return 是否为空
     */
    public static boolean isNotEmpty(OutputStream stream) {
        return !isEmpty(stream);
    }


    /**
     * closeQuietly
     *
     * @param closeable 自动关闭
     */
    public static void close(@Nullable Closeable closeable) {
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
     * 关闭<br>
     * 关闭失败不会抛出异常
     *
     * @param closeable 被关闭的对象
     */
    public static void close(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception var2) {
            }
        }

    }

    /**
     * 尝试关闭指定对象<br>
     * 判断对象如果实现了{@link AutoCloseable}，则调用之
     *
     * @param obj 可关闭对象
     * @since 4.3.2
     */
    public static void closeIfPosible(Object obj) {
        if (obj instanceof AutoCloseable) {
            close((AutoCloseable) obj);
        }

    }


    /**
     * File to list String
     *
     * @param file 读取文件
     * @return 字符串集合
     * @author gclm
     */
    public static List<String> readToLines(File file) {
        List<String> lines = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file);
             BufferedReader in = new BufferedReader(fileReader)) {
            String str;
            while ((str = in.readLine()) != null) {
                lines.add(str);
            }
        } catch (Exception e) {
            throw new ChaosException("读取文件异常", e);
        }
        return lines;
    }

    /**
     * InputStream to list String
     *
     * @param inputStream 读取流
     * @return 字符串集合
     * @author gclm
     */
    public static List<String> readToLines(InputStream inputStream) {
        List<String> empty = ListUtil.empty();
        return IoUtil.readLines(inputStream, CharsetUtils.CHARSET_UTF_8, empty);
    }

    /**
     * InputStream to File
     *
     * @param input    the <code>InputStream</code> to read from
     * @param tempFile write file
     */
    public static void readToFile(InputStream input, File tempFile) {
        try {
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            Files.write(buffer, tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * InputStream to String utf-8
     *
     * @param input the <code>InputStream</code> to read from
     * @return the requested String
     */
    public static String readToString(InputStream input) {
        return readToString(input, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * InputStream to String
     *
     * @param input   the <code>InputStream</code> to read from
     * @param charset the <code>Charset</code>
     * @return the requested String
     */
    public static String readToString(@Nullable InputStream input, Charset charset) {
        try {
            return copyToString(input, charset);
        } catch (IOException e) {
            throw ExceptionUtils.wrapUnchecked(e);
        } finally {
            close(input);
        }
    }

    /**
     * InputStream to byte[]
     *
     * @param input the <code>InputStream</code> to read from
     * @return byte[]
     */
    public static byte[] readToByteArray(@Nullable InputStream input) {
        try {
            return copyToByteArray(input);
        } catch (IOException e) {
            throw ExceptionUtils.wrapUnchecked(e);
        } finally {
            close(input);
        }
    }

    /**
     * Writes chars from a <code>String</code> to bytes on an
     * <code>OutputStream</code> using the specified character encoding.
     * <p>
     * This method uses {@link String#getBytes(String)}.
     * </p>
     *
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

    /**
     * 获得一个Reader
     *
     * @param in      输入流
     * @param charset 字符集
     * @return BufferedReader对象
     */
    public static BufferedReader getReader(InputStream in, Charset charset) {
        if (null == in) {
            return null;
        }
        InputStreamReader reader;
        if (null == charset) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, charset);
        }

        return new BufferedReader(reader);
    }
}
