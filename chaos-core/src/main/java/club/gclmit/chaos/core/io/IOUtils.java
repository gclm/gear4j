package club.gclmit.chaos.core.io;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import javax.annotation.Nullable;
import java.io.*;

/**
 * <p>
 * IO 流工具类
 * </p>
 *
 * @author gclm
 */
public class IOUtils extends IoUtil {

    /**
     * IO流是否为空
     *
     * @param stream IO流
     * @return 是否为空
     */
    public static boolean isEmpty(@Nullable InputStream stream) {
        try {
            return stream == null || stream.available() == 0;
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * IO流是否为空
     *
     * @param stream IO流
     * @return 是否为空
     */
    public static boolean isEmpty(@Nullable OutputStream stream) {
        return stream == null;
    }

    /**
     * IO流是否为空
     *
     * @param stream IO流
     * @return 是否为空
     */
    public static boolean isNotEmpty(@Nullable InputStream stream) {
        return !isEmpty(stream);
    }

    /**
     * IO流是否为空
     *
     * @param stream IO流
     * @return 是否为空
     */
    public static boolean isNotEmpty(@Nullable OutputStream stream) {
        return !isEmpty(stream);
    }
}
