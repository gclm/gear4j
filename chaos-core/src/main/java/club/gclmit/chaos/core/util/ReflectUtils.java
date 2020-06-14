package club.gclmit.chaos.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 反射工具类
 * </p>
 *
 * @author gclm
 */
public class ReflectUtils {

    private ReflectUtils() {
    }

    /**
     *  获取 Object 的 Class
     *
     * @author gclm
     * @param object Object
     * @return Class
     */
    public static Class<?> getClass(Object object) {
        return object.getClass();
    }

    /**
     *  获取 Object 的所有参数
     *
     * @author gclm
     * @param object  Object
     * @return java.lang.reflect.Field[]
     */
    public static List<Field> getFields(Object object) {
        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = getClass(object);
        //当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;
    }

    /**
     *  获取 类名
     *
     * @author gclm
     * @param object Object
     * @return java.lang.String
     */
    public static String getClassName(Object object) {
        return getClass(object).getName();
    }

    /**
     *  获取 简单类名
     *
     * @author gclm
     * @param object Object
     * @return java.lang.String
     */
    public static String getClassSimpleName(Object object) {
        return getClass(object).getSimpleName();
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz Class
     * @return 返回第一个类型
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz Class
     * @param index 返回某下标的类型
     * @return Class
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }


}
