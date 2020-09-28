package club.gclmit.chaos.http.io;

import club.gclmit.chaos.http.text.StringUtils;

import java.io.PrintWriter;

/**
 * 快速的 PrintWriter，用来处理异常信息，转化为字符串
 * <p>
 * 1. 默认容量为 256
 * </p>
 * @author gclm
 * @since 2020/9/27 17:33
 * @since 1.8
 */
public class FastStringPrintWriter extends PrintWriter {

    private final FastStringWriter writer;

    public FastStringPrintWriter() {
        this(256);
    }

    public FastStringPrintWriter(int initialSize) {
        super(new FastStringWriter(initialSize));
        this.writer = (FastStringWriter) out;
    }

    /**
     * Throwable printStackTrace，只掉用了该方法
     *
     * @param x Object
     */
    @Override
    public void println(Object x) {
        writer.write(String.valueOf(x));
        writer.write(StringUtils.LF);
    }

    @Override
    public String toString() {
        return writer.toString();
    }
}

