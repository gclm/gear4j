package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.lang.Snowflake;
import java.util.UUID;

/**
 * <p>
 * id 生成器
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/14 9:35 下午
 * @version: V1.0
 * @since 1.8
 */
public class IDUtils {

    /**
     * @return 随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     * @since 3.2.2
     */
    public static String simpleUUID() {
        return StringUtils.replace(randomUUID(),"-","");
    }

    /**
     * <p>
     *  根据选择生成器类型，生成唯一ID
     * </p>
     *
     * @author 孤城落寞
     * @param: type 证书类型
     * @date 2019/10/22 21:16
     * @return: java.lang.Long
     * @throws
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
     * @param: type 证书类型
     * @date 2019/10/22 21:15
     * @return: java.lang.String
     * @throws
     */
    public static String stringSnowflakeId() {
        return String.valueOf(snowflakeId());
    }

}
