package club.gclmit.chaos.core.id;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * Id 控制器
 *
 * @author gclm
 * @since 1.8
 */
public class IdUtils extends IdUtil {

    /**
     * 非单例创建 YeinGid 算法生成器。
     *
     * @param workerId 标识 {@code 0-131071 }区间内的数字
     * @return {@link YeinGid}
     */
    public static YeinGid createYeinGid(Integer workerId) {
        return new YeinGid(workerId);
    }

    /**
     * 获取YeinGid
     *
     * @return YeinGid 全局id
     */
    public static String getYeinGid() {
        int workId = RandomUtil.randomInt(0, 131071);
        return getYeinGid(workId).toHexString();
    }

    /**
     * 获取单例的YeinGid算法生成器对象<br>
     *
     * @param workerId 标识 {@code 0-131071 }区间内的数字
     * @return {@link YeinGid}
     */
    public static YeinGid getYeinGid(Integer workerId) {
        return Singleton.get(YeinGid.class, workerId);
    }
}
