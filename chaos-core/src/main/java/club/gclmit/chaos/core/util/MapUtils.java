package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.bean.convert.ConvertUtils;
import cn.hutool.core.util.ReflectUtil;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Map相关工具类
 *
 * @author gclm
 */
@UtilityClass
public class MapUtils{

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
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && false == map.isEmpty();
    }

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptyMap()}
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param set 提供的集合，可能为null
     * @return 原集合，若为null返回空集合
     * @since 4.6.3
     */
    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> set) {
        return (null == set) ? Collections.emptyMap() : set;
    }

    /**
     * 如果给定Map为空，返回默认Map
     *
     * @param <T>        集合类型
     * @param <K>        键类型
     * @param <V>        值类型
     * @param map        Map
     * @param defaultMap 默认Map
     * @return 非空（empty）的原Map或默认Map
     * @since 4.6.9
     */
    public static <T extends Map<K, V>, K, V> T defaultIfEmpty(T map, T defaultMap) {
        return isEmpty(map) ? defaultMap : map;
    }


    //  Map <==> Object
    // -----------------------------------------------------------------------------------------------

    /**
     *  Map to Object
     *
     * @author gclm
     * @param map      待转换的Map
     * @param beanClass  生成的Object
     * @return java.lang.Object
     * @throws Exception 反射异常
     */
    public static Object mapToObject(Map map, Class<?> beanClass) throws Exception {

        if (map == null) {
            return null;
        }

        Object obj = ReflectUtil.newInstance(beanClass);
        Field[] fields = ReflectUtil.getFields(beanClass);

        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if (map.containsKey(name)) {
                Object value = map.get(name);
//                if (ArrayUtils.isArray(value)) {
//                    value = Array.get(value,0);
//                }
                field.set(obj, ConvertUtils.convert(value, field.getType()));
            }
            field.setAccessible(true);
        }
        return obj;
    }

    /**
     *  Object To Map
     *
     * @author gclm
     * @param obj 待转换的Object
     * @return java.util.Map
     * @exception Exception 反射异常
     */
    public static Map objectToMap(Object obj) throws Exception {

        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

}
