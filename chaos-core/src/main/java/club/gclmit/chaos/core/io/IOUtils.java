package club.gclmit.chaos.core.io;

import club.gclmit.chaos.core.exception.ChaosException;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.io.*;
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

    /**
     * 从文件中读取内容
     *
     * @author gclm
     * @param file 读取文件
     * @return 字符串集合
     */
    public static List<String> readLines(File file) {
        List<String> lines = new ArrayList<>();
        try(FileReader fileReader = new FileReader(file);
            BufferedReader in = new BufferedReader(fileReader)) {
            String str;
            while ((str = in.readLine()) != null) {
                lines.add(str);
            }
        } catch (Exception e) {
            throw new ChaosException("读取文件异常",e);
        }
        return null;
    }
}
