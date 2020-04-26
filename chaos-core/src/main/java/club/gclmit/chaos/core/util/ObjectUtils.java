package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        } else if (object instanceof String && "".equals(object.toString().trim()) &&  object.toString().trim().length() > 0 ){
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
        Class<?> clazz = ReflectUtils.getClass(object);
        Field[] fields = clazz.getDeclaredFields();
        /**
         * 获取 参数属性封装
         */
        StringBuilder fieldBuilder = StringUtils.builder();
        for (Field field : fields){
            field.setAccessible(true);
            try {
                fieldBuilder.append(field.getName()).append("=").append(field.get(object)).append(", ");
            } catch (IllegalAccessException e) {
                throw new ChaosCoreException("通过反射拼接ToString异常",e);
            }
            field.setAccessible(false);
        }
        String result = StringUtils.subBefore(fieldBuilder, ", ", true);
        return  StringUtils.builder(clazz.getSimpleName()).append("{").append(result).append("}").toString();
    }
}
