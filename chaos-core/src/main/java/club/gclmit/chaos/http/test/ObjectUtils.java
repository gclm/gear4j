package club.gclmit.chaos.http.test;

import club.gclmit.chaos.http.exception.ChaosCoreException;
import club.gclmit.chaos.http.lang.Assert;
import club.gclmit.chaos.http.text.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
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
 * @author gclm
 */
public class ObjectUtils extends org.springframework.util.ObjectUtils {

    private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    private ObjectUtils() {
    }

    /**
     * 判断对象为null
     *
     * @param object 对象
     * @return 对象是否为空
     */
    public static boolean isNull(@Nullable Object object) {
        return Objects.isNull(object);
    }

    /**
     * 判断对象不为null
     *
     * @param object 对象
     * @return 对象是否不为空
     */
    public static boolean isNotNull(@Nullable Object object) {
        return Objects.nonNull(object);
    }

    /**
     * 判断数组不为空
     *
     * @param array 数组
     * @return 数组是否为空
     */
    public static boolean isNotEmpty(@Nullable Object[] array) {
        return false == isEmpty(array);
    }

    /**
     * 判断对象不为空
     *
     * @param obj 数组
     * @return 数组是否为空
     */
    public static boolean isNotEmpty(@Nullable Object obj) {
        return false == isEmpty(obj);
    }

    /**
     * 对象 eq
     *
     * @param o1 Object
     * @param o2 Object
     * @return 是否eq
     */
    public static boolean equals(@Nullable Object o1, @Nullable Object o2) {
        return Objects.equals(o1, o2);
    }

    /**
     * 比较两个对象是否不相等。<br>
     *
     * @param o1 对象1
     * @param o2 对象2
     * @return 是否不eq
     */
    public static boolean isNotEqual(Object o1, Object o2) {
        return false == equals(o1, o2);
    }

    /**
     * 返回对象的 hashCode
     *
     * @param obj Object
     * @return hashCode
     */
    public static int hashCode(@Nullable Object obj) {
        return Objects.hashCode(obj);
    }

    /**
     * 判断对象为true
     *
     * @param object 对象
     * @return 对象是否为true
     */
    public static boolean isTrue(@Nullable Boolean object) {
        return Boolean.TRUE.equals(object);
    }

    /**
     * 判断对象为false
     *
     * @param object 对象
     * @return 对象是否为false
     */
    public static boolean isFalse(@Nullable Boolean object) {
        return object == null || Boolean.FALSE.equals(object);
    }

    /**
     * 如果对象为null，返回默认值
     *
     * @param object       Object
     * @param defaultValue 默认值
     * @return Object
     */
    public static Object defaultIfNull(@Nullable Object object, Object defaultValue) {
        return object != null ? object : defaultValue;
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
     * 判断一个对象的 所有属性是否为空
     *
     * @param object Object
     * @return boolean
     * @throws IllegalAccessException 反射异常
     */
    public static boolean objectFieldIsNull(Object object) throws IllegalAccessException {

        for (Field field : object.getClass().getDeclaredFields()) {
            /**
             * 配置获取私有属性的值
             */
            field.setAccessible(true);
            /**
             * 这里忽略static final 类型的属性，如若不需要可以去掉
             */
            if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            /**
             * field.get(Object obj) 获取属性的值
             */
            if (!isEmpty(field.get(object))) {
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
     *
     * @param object    Object
     * @param fieldName 属性名
     * @return boolean
     * @throws NoSuchFieldException   不存在该属性异常
     * @throws IllegalAccessException 反射异常
     */
    public static boolean objectSingleFieldIsNull(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {

        logger.info("对象：" + object + "属性名：" + fieldName);

        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        if (!isEmpty(field.get(object))) {
            logger.info("field值:" + field.get(object));
            return false;
        }
        field.setAccessible(false);
        return true;
    }

    /**
     * 通过反射设置对象的属性
     *
     * @param bean       object
     * @param fieldName  属性名
     * @param fieldValue 属性值
     * @return java.lang.Object
     * @throws NoSuchFieldException      不存在该属性异常
     * @throws NoSuchMethodException     不存在该方法异常
     * @throws InvocationTargetException 反射异常
     * @throws IllegalAccessException    反射异常
     * @author gclm
     */
    public static Object setObjectField(Object bean, String fieldName, String fieldValue) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<?> beanClass = bean.getClass();
        Field field = beanClass.getDeclaredField(fieldName);
        field.setAccessible(true);

        /**
         * 首字母转换成大写
         */
        char[] cs = fieldName.toCharArray();
        cs[0] -= 32;

        String fieldMethodName = new StringBuilder("set").append(String.valueOf(cs)).toString();
        Method method = beanClass.getDeclaredMethod(fieldMethodName, field.getType());
        method.invoke(bean, fieldValue);
        field.setAccessible(true);
        return bean;
    }


    /**
     * 通过反射获取 Object 的 属性
     *
     * @param object Object
     * @return java.lang.String
     * @author gclm
     */
    public static String toString(Object object) {
        Assert.notNull(object, "object 不能为空");
        List<Field> fieldList = new ArrayList<>();
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
        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                Object value = isEmpty(field.get(object)) ? null : field.get(object);
                fieldBuilder.append(field.getName()).append("=").append(value).append(", ");
            } catch (IllegalAccessException e) {
                throw new ChaosCoreException("通过反射拼接ToString异常", e);
            }
            field.setAccessible(false);
        }
        String result = StringUtils.substringBefore(fieldBuilder.toString(), ", ");
        return StringUtils.builder(className).append("{").append(result).append("}").toString();
    }
}
