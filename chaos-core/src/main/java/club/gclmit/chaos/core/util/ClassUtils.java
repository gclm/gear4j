package club.gclmit.chaos.core.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

/**
 * 类工具类
 *
 * @author gclm
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class ClassUtils extends ClassUtil {

    /**
     * Determine if the given type is assignable from the given value,
     * assuming setting by reflection. Considers primitive wrapper classes
     * as assignable to the corresponding primitive types.
     * @param type the target type
     * @param value the value that should be assigned to the type
     * @return if the type is assignable from the value
     */
    public static boolean isAssignableValue(Class<?> type, @Nullable Object value) {
        Assert.notNull(type, "Type must not be null");
        return (value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive());
    }

}
