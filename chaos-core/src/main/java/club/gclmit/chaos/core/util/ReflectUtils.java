package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.exception.ChaosCoreException;

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
 * @author: gclm
 * @date: 2020/4/16 6:48 下午
 * @version: V1.0
 * @since 1.8
 */
public class ReflectUtils {

    /**
     *  获取 Object 的 Class
     *
     * @author gclm
     * @param: object
     * @date 2020/4/16 6:55 下午
     * @return: java.lang.Class<?>
     */
    public static Class<?> getClass(Object object) {
        return object.getClass();
    }

    /**
     *  获取 Object 的所有参数
     *
     * @author gclm
     * @param: object
     * @date 2020/4/16 7:08 下午
     * @return: java.lang.reflect.Field[]
     */
    public static List<Field> getFields(Object object) {
        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = getClass(object);
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }

    /**
     *  获取 类名
     *
     * @author gclm
     * @param: object
     * @date 2020/4/16 7:10 下午
     * @return: java.lang.String
     */
    public static String getClassName(Object object) {
        return getClass(object).getName();
    }

    /**
     *  获取 简单类名
     *
     * @author gclm
     * @param: object
     * @date 2020/4/16 7:11 下午
     * @return: java.lang.String
     */
    public static String getClassSimpleName(Object object) {
        return getClass(object).getSimpleName();
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz
     * @return 返回第一个类型
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz
     * @param index 返回某下标的类型
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
