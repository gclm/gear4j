package club.gclmit.chaos.core.utils;

import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.cglib.beans.BeanMap;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {
		Map<String, Object> map = new HashMap<>();
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
	 */
	public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz) throws Exception {
		// 1、通过字节码对象创建空的实例
		T bean = clazz.getDeclaredConstructor().newInstance();
		// 2、通过 Introspector 类把bean对象信息封装到 beanInfo 中
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
		// 3、通过 getPropertyDescriptors() 获取一个属性(get/set)数组
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		// 4、遍历该数组，把获取的名字作为 map 的 key，通过 key 取出对应的 value 值
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String key = propertyDescriptor.getName();
			Object value = map.get(key);
			if (ArrayUtils.isArray(value)) {
				value = Array.get(value, 0);
			}
			Class<?> propertyType = propertyDescriptor.getPropertyType();
			Method writeMethod = propertyDescriptor.getWriteMethod();
			writeMethod.invoke(bean, ConvertUtils.convert(value, propertyType));
		}
		return bean;
	}

}
