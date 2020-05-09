package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

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

    private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    // 为空
    // ----------------------------------------------------------------------

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    /**
     * Enumeration是否为空
     *
     * @param enumeration {@link Enumeration}
     * @return 是否为空
     */
    public static boolean isEmpty(Enumeration<?> enumeration) {
        return null == enumeration || false == enumeration.hasMoreElements();
    }

    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterable<?> iterable) {
        return null == iterable || isEmpty(iterable.iterator());
    }

    /**
     * Iterator是否为空
     *
     * @param Iterator Iterator对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterator<?> Iterator) {
        return null == Iterator || false == Iterator.hasNext();
    }

    /**
     * <p>Checks if an array of primitive booleans is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final boolean[] array) {
        return ArrayUtils.getLength(array) == 0;
    }

    /**
     * <p>Checks if an array of primitive bytes is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final byte[] array) {
        return ArrayUtils.getLength(array) == 0;
    }

    /**
     * <p>Checks if an array of primitive chars is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final char[] array) {
        return ArrayUtils.getLength(array) == 0;
    }

    /**
     * <p>Checks if an array of primitive doubles is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final double[] array) {
        return ArrayUtils.getLength(array) == 0;
    }

    /**
     * <p>Checks if an array of primitive floats is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final float[] array) {
        return ArrayUtils.getLength(array) == 0;
    }
    
    /**
     * <p>Checks if an array of primitive ints is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final int[] array) {
        return ArrayUtils.getLength(array) == 0;
    }

    /**
     * <p>Checks if an array of primitive longs is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final long[] array) {
        return ArrayUtils.getLength(array) == 0;
    }

    /**
     * <p>Checks if an array of Objects is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final Object[] array) {
        return ArrayUtils.getLength(array) == 0;
    }

    /**
     * <p>Checks if an array of primitive shorts is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final short[] array) {
        return ArrayUtils.getLength(array) == 0;
    }

    // 不为空
    // ----------------------------------------------------------------------
    
    /**
     * <p>Checks if an array of primitive booleans is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final boolean[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>Checks if an array of primitive bytes is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final byte[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>Checks if an array of primitive chars is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final char[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>Checks if an array of primitive doubles is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final double[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>Checks if an array of primitive floats is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final float[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>Checks if an array of primitive ints is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final int[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>Checks if an array of primitive longs is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final long[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>Checks if an array of primitive shorts is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final short[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>Checks if an array of Objects is not empty and not {@code null}.
     *
     * @param <T> the component type of the array
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static <T> boolean isNotEmpty(final T[] array) {
        return !isEmpty(array);
    }

    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Enumeration是否为空
     *
     * @param enumeration {@link Enumeration}
     * @return 是否为空
     */
    public static boolean isNotEmpty(Enumeration<?> enumeration) {
        return !isEmpty(enumeration);
    }

    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     */
    public static boolean isNotEmpty(Iterable<?> iterable) {
        return !isEmpty(iterable);
    }

    /**
     * Iterator是否为空
     *
     * @param Iterator Iterator对象
     * @return 是否为空
     */
    public static boolean isNotEmpty(Iterator<?> Iterator) {
        return !isEmpty(Iterator);
    }


    // 其他方法
    // ----------------------------------------------------------------------


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
     * 判断一个对象的 所有属性是否为空
     * @details 孤城落寞 2019-02-15 10:50
     * @param object
     * @return boolean
     */
    public static boolean objectFieldIsNull(Object object) throws IllegalAccessException {

        for (Field field : object.getClass().getDeclaredFields()){

            /**
             * 配置获取私有属性的值
             */
            field.setAccessible(true);

            /**
             * 这里忽略static final 类型的属性，如若不需要可以去掉
             */
            if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())){
                continue;
            }

            /**
             * field.get(Object obj) 获取属性的值
             */
            if (!isEmpty(field.get(object))){
                return false;
            }

            /**
             * 关闭获取私有属性的值
             */
            field.setAccessible(false);
        }
        return true;
    }

    /**
     * 判断一个对象某个属性是否为空
     * @details 孤城落寞 2019-02-15 14:51
     * @param object
     * @param fieldName
     * @return boolean
     */
    public static boolean objectSingleFieldIsNull(Object object,String fieldName) throws NoSuchFieldException, IllegalAccessException {

        logger.info("对象："+object+"属性名："+fieldName);

        Field field = object.getClass().getDeclaredField(fieldName);

        field.setAccessible(true);

        if (!isEmpty(field.get(object))){
            logger.info("field值:"+field.get(object));
            return  false;
        }

        field.setAccessible(false);
        return true;
    }

    /**
     *  通过反射设置对象的属性
     *
     * @author gclm
     * @param: bean
     * @param: fieldName
     * @param: fieldValue
     * @return: java.lang.Object
     * @since
     */
    public static Object setObjectField(Object bean,String fieldName,String fieldValue) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<?> beanClass = bean.getClass();

        Field field = beanClass.getDeclaredField(fieldName);

        field.setAccessible(true);

        /**
         * 首字母转换成大写
         */
        char[] cs = fieldName.toCharArray();
        cs[0]  -= 32;

        String fieldMethodName = new StringBuilder("set").append(String.valueOf(cs)).toString();

        Method method = beanClass.getDeclaredMethod(fieldMethodName, field.getType());

        method.invoke(bean,fieldValue);

        field.setAccessible(true);

        return bean;
    }

    /**
     * Object 对象非空判断。
     *   如果对象为空则返回 true,非空则返回 false
     * 目前只支持 String、Number、File、Collection、List、Map、Object[] 类型
     * @details 孤城落寞 2019-02-15 10:40
     * @param object
     * @return boolean
     */
    public static boolean isEmpty(Object object){
        if(object == null){
            return  true;
        } else if (object instanceof String && StringUtils.isEmpty((String)object)){
            return  true;
        }else if (object instanceof  Integer && (Integer) object == 0){
            return  true;
        } else if (object instanceof Number && ((Number) object).doubleValue() < 0){
            return true;
        } else if (object instanceof File && (((File) object).isDirectory() || !((File)object).exists())){
            return true;
        } else if (object instanceof Collection && ((Collection) object).isEmpty()){
            return  true;
        } else if (object instanceof List && (((List) object).isEmpty() || ((List) object).size() == 0)){
            return  true;
        } else if (object instanceof Map && (((Map) object).isEmpty() || ((Map) object).size() == 0)){
            return true;
        } else if (object instanceof Object[] && ((Object[]) object).length == 0){
            return  true;
        }
        return false;
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
        Assert.notNull(object,"object 不能为空");
        List<Field> fieldList = new ArrayList<>() ;
        Class clazz = object.getClass();
        String className = clazz.getSimpleName();

        /**
         *  1. 当父类为null的时候说明到达了最上层的父类(Object类).
         *  2. 得到父类,然后赋给自己
         */
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        /**
         * 获取 参数属性封装
         */
        StringBuilder fieldBuilder = StringUtils.builder();
        for (Field field : fieldList){
            field.setAccessible(true);
            try {
                Object value = isEmpty(field.get(object)) ? null : field.get(object);
                fieldBuilder.append(field.getName()).append("=").append(value).append(", ");
            } catch (IllegalAccessException e) {
                throw new ChaosCoreException("通过反射拼接ToString异常",e);
            }
            field.setAccessible(false);
        }
        String result = StringUtils.subBefore(fieldBuilder, ", ", true);
        return  StringUtils.builder(className).append("{").append(result).append("}").toString();
    }
}
