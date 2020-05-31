package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.lang.Snowflake;
import lombok.experimental.UtilityClass;

/**
 * <p>
 * id 生成器
 * </p>
 *
 * @author gclm
 */
@UtilityClass
public class IdUtils {

    /**
     * 随机UUID
     * @author 孤城落寞
     * @return java.lang.String
     */
    public static String randomUUID() {
        return UUIDUtils.getUUID();
    }

    /**
     * 简化的UUID，去掉了横线
     * @author 孤城落寞
     * @return java.lang.String
     */
    public static String simpleUUID() {
        return UUIDUtils.getSimpleUUID();
    }

    /**
     * <p>
     *  根据选择生成器类型，生成唯一ID
     * </p>
     *
     * @author 孤城落寞
     * @return java.lang.Long
     */
    public static Long snowflakeId() {
        return new Snowflake(0, 0).nextId();
    }

    /**
     * <p>
     *  根据选择生成器类型，生成唯一ID
     * </p>
     *
     * @author 孤城落寞
     * @return java.lang.String
     */
    public static String stringSnowflakeId() {
        return String.valueOf(snowflakeId());
    }

}
