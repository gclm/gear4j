package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.lang.Snowflake;
import club.gclmit.chaos.core.text.StringUtils;

/**
 * <p>
 * id 生成器
 * </p>
 *
 * @author gclm
 */
public class IdUtils {

    private IdUtils() {
    }

    /**
     * 随机UUID
     * @author 孤城落寞
     * @return java.lang.String
     */
    public static String randomUUID() {
        return StringUtils.getUUID();
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
