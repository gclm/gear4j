package club.gclmit.chaos.web.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 版本注解
 * </p>
 *
 * @author gclm
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FastApi {

    /**
     * 标识版本号，从1开始
     * @return 默认值 1
     */
    int value() default 1;

    /**
     * Swagger标识版本号，从v1开始
     * @return 默认值 v1
     */
    String[] swagger() default "v1";
}
