package club.gclmit.gear4j.core.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

import com.tuyang.beanutils.BeanCopyUtils;

import club.gclmit.gear4j.core.exception.ChaosException;
import cn.hutool.core.util.ReflectUtil;

/**
 * Bean Copy Utils
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/1/11 3:29 PM
 * @since jdk11
 */
public class BeanUtils extends BeanCopyUtils {

	public BeanUtils() {
	}

	/**
	 * 将对象属性转化为map结合
	 *
	 * @param bean 待转换的object
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {
		Map<String, Object> map = new HashMap<>(33);
		if (bean != null) {
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				map.put(key + "", beanMap.get(key));
			}
		}
		return map;
	}

	/**
	 * 将map集合中的数据转化为指定对象的同名属性中
	 *
	 * @param map   待转换的Map
	 * @param clazz 生成的Object
	 * @throws Exception 反射异常
	 */
	public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz) throws Exception {
		if (map == null) {
			return null;
		}

		T obj = ReflectUtil.newInstance(clazz);
		Field[] fields = ReflectUtil.getFields(clazz);

		for (Field field : fields) {
			field.setAccessible(true);
			String name = field.getName();
			if (map.containsKey(name)) {
				Object value = map.get(name);
				if (ArrayUtils.isArray(value)) {
					if (Array.getLength(value) > 1) {
						throw new ChaosException("不支持数组参数");
					}
					value = Array.get(value, 0);
				}
				field.set(obj, ConvertUtils.convert(value, field.getType()));
			}
			field.setAccessible(true);
		}
		return obj;
	}
}
