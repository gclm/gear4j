package club.gclmit.chaos.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 基于FastJSON 封装
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/13 10:15 上午
 * @version: V1.0
 * @since 1.8
 */
public class JsonUtils {

    private static Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private static SerializeConfig mapping = new SerializeConfig();

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        mapping.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        mapping.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        mapping.put(java.sql.Time.class, new SimpleDateFormatSerializer("HH:mm:ss"));
    }

    public static SerializeConfig put(Class<?> clazz, SerializeFilter filter) {
        mapping.addFilter(clazz, filter);
        return mapping;
    }

    public static SerializeConfig put(Class<?> clazz, ObjectSerializer serializer) {
        mapping.put(clazz, serializer);
        return mapping;
    }

    public static <T> T toBean(String jsonString, Class<T> tt) {
        try {
            if (StringHelper.isBlank(jsonString)) {
                return null;
            }
            T t = JSON.parseObject(jsonString, tt);
            return t;
        } catch (Exception e) {
            log.error(jsonString, e);
            return null;
        }
    }

    public static <T> List<T> toList(String jsonString, Class<T> clazz) {
        try {
            if (StringHelper.isBlank(jsonString)) {
                return null;
            }
            List<T> list = JSON.parseArray(jsonString, clazz);
            return list;
        } catch (Exception e) {
            log.error(jsonString, e);
            return null;
        }
    }

    /**
     *
     * @param bean
     * @return
     */
    public static String toFormatedJson(Object bean) {
        return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat);
    }

    /**
     *
     * @param bean
     * @return
     */
    public static String toJson(Object bean) {
        return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 可以返回null的key值
     * @param bean
     * @return
     */
    public static String toJsonAboutNull(Object bean) {
        return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullStringAsEmpty);
    }

    /**
     *
     * @param bean
     * @param serializeFilter
     * @return
     */
    public static String toJson(Object bean, SerializeFilter serializeFilter) {
        if (serializeFilter != null) {
            return JSON.toJSONString(bean, mapping, serializeFilter, SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
