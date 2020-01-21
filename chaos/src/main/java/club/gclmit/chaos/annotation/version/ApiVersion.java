package club.gclmit.chaos.annotation.version;

import java.lang.annotation.*;

/**
 * <p>
 * 版本号注解
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/17 11:41 上午
 * @version: V1.0
 * @since 1.8
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * 标识版本号，从1开始
     */
    int value() default 1;

}
