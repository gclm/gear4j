package club.gclmit.chaos.core.util;

/**
 * <p>
 *  数据相关服务
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/24 10:51 上午
 * @version: V1.0
 * @since 1.8
 */
public class DBUtils {

    /**
     *  判断数据是否修改成功
     *
     * @author gclm
     * @param: result
     * @date 2020/3/24 10:51 上午
     * @return: boolean
     * @throws
     */
    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }
}
