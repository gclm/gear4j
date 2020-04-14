package club.gclmit.chaos.core;

import club.gclmit.chaos.core.lang.Snowflake;

/**
 * <p>
 * ID 生成器
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/7 0:17
 * @version: V1.0
 */
public class IDHelperTest {

    public static void main(String[] args) {
        System.out.println(Snowflake.getLongId());
        System.out.println(Snowflake.getStringId());
    }
}
