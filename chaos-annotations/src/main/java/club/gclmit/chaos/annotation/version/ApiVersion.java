package club.gclmit.chaos.annotation.version;

import java.lang.annotation.*;

/**
 * <p>
 * 版本号注解
 * </p>
 *
 * @author gclm
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * 标识版本号，从1开始
     * @return 默认值 1
     */
    int value() default 1;

}
