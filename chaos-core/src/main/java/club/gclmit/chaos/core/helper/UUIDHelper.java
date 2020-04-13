package club.gclmit.chaos.core.helper;

import java.util.UUID;

/**
 * <p>
 * uuid 工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/13 1:32 下午
 * @version: V1.0
 * @since 1.8
 */
public class UUIDHelper {

    /**
     *  uuid 生成器
     *
     * @author gclm
     * @date 2020/4/13 1:32 下午
     * @return: java.lang.String
     * @throws
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
