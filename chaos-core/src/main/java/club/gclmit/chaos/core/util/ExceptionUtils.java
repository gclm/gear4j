package club.gclmit.chaos.core.util;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;

/**
 * 异常工具类
 *
 * @author gclm
 */
@UtilityClass
public class ExceptionUtils extends ExceptionUtil {

    /**
     * 将CheckedException转换为UncheckedException.
     *
     * @param e Throwable
     * @return {RuntimeException}
     */
    public static RuntimeException wrapUnchecked(Throwable e) {
        if (e instanceof Error) {
            throw (Error) e;
        } else if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException ||
                e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return unWrapRuntime(((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        return unWrapRuntime(e);
    }

    /**
     * 不采用 RuntimeException 包装，直接抛出，使异常更加精准
     *
     * @param throwable Throwable
     * @param <T>       泛型标记
     * @return Throwable
     * @throws T 泛型
     */
    @SuppressWarnings("unchecked")
    private static <T extends Throwable> T unWrapRuntime(Throwable throwable) throws T {
        throw (T) throwable;
    }
}
