package club.gclmit.chaos.core.helper.id;

/**
 * <p>
 * ID 生成仓库
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-25 20:01:00
 * @version: V1.0
 * @since JDK1.8
 */
public class IDHelper {

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
    public static Long getLongId() {
        return new SnowflakeDistributeId(0, 0).nextId();
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
    public static String getStringId() {
        return String.valueOf(new SnowflakeDistributeId(0, 0).nextId());
    }
}
