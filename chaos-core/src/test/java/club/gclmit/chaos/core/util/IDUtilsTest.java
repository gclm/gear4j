package club.gclmit.chaos.core.util;

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

        System.out.println("雪花算法："+IDUtils.snowflakeId());
        System.out.println("雪花算法："+IDUtils.stringSnowflakeId());

        System.out.println("UUID:" + IDUtils.randomUUID());
        System.out.println("Simple-UUID:" + IDUtils.simpleUUID());
    }
}
