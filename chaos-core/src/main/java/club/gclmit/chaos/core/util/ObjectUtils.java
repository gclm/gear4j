package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import java.lang.reflect.Field;

/**
 * <p>
 * 常见Object操作封装
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/15 8:39 上午
 * @version: V1.0
 * @since 1.8
 */
public class ObjectUtils {

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * <ol>
     * <li>obj1 == null &amp;&amp; obj2 == null</li>
     * <li>obj1.equals(obj2)</li>
     * </ol>
     * 1. obj1 == null &amp;&amp; obj2 == null 2. obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     */
    public static boolean equals(Object obj1, Object obj2) {
        return (obj1 == obj2) || (obj1 != null && obj1.equals(obj2));
    }


    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
            return false;
        }
        return obj.getClass().isArray();
    }

    /**
     * 通过反射获取 Object 的 属性
     *
     * @author gclm
     * @param: object
     * @date 2020/1/15 3:07 下午
     * @return: java.lang.String
     */
    public static String toString(Object object){
        Class<?> clazz = object.getClass();
        String className = clazz.getName();
        Field[] fields = clazz.getFields();
        StringBuilder builder = new StringBuilder().append(className).append("{");
        for (Field field : fields){
            field.setAccessible(true);
            try {
                builder.append(field.getName()).append("=").append(field.get(object)).append(", ");
            } catch (IllegalAccessException e) {
              throw new ChaosCoreException("通过反射拼接ToString异常",e);
            }
            field.setAccessible(false);
        }
        builder.append("}");
        return builder.toString();
    }
}
