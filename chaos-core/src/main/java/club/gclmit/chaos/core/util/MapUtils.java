package club.gclmit.chaos.core.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Map相关工具类
 *
 * @author gclm
 */
public class MapUtils extends MapUtil {

    /**
     *  Map to Object
     *
     * @author gclm
     * @param map      待转换的Map
     * @param beanClass :  生成的Object
     * @return java.lang.Object
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
            String type = field.getType().getSimpleName();
            if (map.containsKey(name)) {
                Object value = map.get(name);
                field.set(obj, ConvertUtils.convert(value, field.getType()));
            }
            field.setAccessible(true);
        }
        return obj;
    }

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
