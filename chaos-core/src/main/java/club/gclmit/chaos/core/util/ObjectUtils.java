package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.collection.CollectionUtils;
import club.gclmit.chaos.core.collection.ListUtils;
import club.gclmit.chaos.core.collection.MapUtils;
import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.io.file.FileUtils;
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
 * @author gclm
 */
public class ObjectUtils {

    private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    /**
     * Object 对象非空判断。
     * 如果对象为空则返回 true,非空则返回 false
     * 目前只支持 String、Number、File、Collection、List、Map、Object[] 类型
     *
     * @param object Object
     * @return boolean
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return StringUtils.isEmpty((CharSequence) object);
        } else if (object instanceof Integer) {
            return NumberUtils.isEmpty((Integer) object);
        } else if (object instanceof Number) {
            return NumberUtils.isEmpty((Number) object);
        } else if (object instanceof File) {
            return FileUtils.isEmpty((File) object);
        } else if (object instanceof Collection) {
            return CollectionUtils.isEmpty((Iterable<?>) object);
        } else if (object instanceof List) {
            return ListUtils.isEmpty((List) object);
        } else if (object instanceof Map) {
            return MapUtils.isEmpty((Map) object);
        } else if (object instanceof Object[] && ((Object[]) object).length == 0) {
            return true;
        }
        return false;
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
     * @param object Object
     * @return boolean
     * @throws IllegalAccessException 反射异常
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
     * @param object     Object
     * @param fieldName  属性名
     * @return boolean
     * @throws NoSuchFieldException    不存在该属性异常
     * @throws IllegalAccessException  反射异常
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
     * 通过反射设置对象的属性
     *
     * @author gclm
     * @param bean        object
     * @param fieldName   属性名
     * @param fieldValue  属性值
     * @return java.lang.Object
     * @throws NoSuchFieldException         不存在该属性异常
     * @throws NoSuchMethodException        不存在该方法异常
     * @throws InvocationTargetException    反射异常
     * @throws IllegalAccessException       反射异常
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
     * @author gclm
     * @param object  Object
     * @return java.lang.String
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
        String result = StringUtils.subBefore(fieldBuilder, ", ", true);
        return StringUtils.builder(className).append("{").append(result).append("}").toString();
    }
}
