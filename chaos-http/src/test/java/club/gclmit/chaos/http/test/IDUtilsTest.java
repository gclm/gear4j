package club.gclmit.chaos.http.test;

import club.gclmit.chaos.core.util.IdUtils;

/**
 * <p>
 * ID 生成器
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/7 0:17
 * @version: V1.0
 */
public class IDUtilsTest {

    public static void main(String[] args) {

        System.out.println("雪花算法："+ IdUtils.snowflakeId());
        System.out.println("雪花算法："+ IdUtils.stringSnowflakeId());

        System.out.println("UUID:" + IdUtils.randomUUID());
        System.out.println("Simple-UUID:" + IdUtils.simpleUUID());
    }
}
