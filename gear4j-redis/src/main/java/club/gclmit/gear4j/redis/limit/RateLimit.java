package club.gclmit.gear4j.redis.limit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RateLimit 限流注解
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/17 16:36
 * @since jdk11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 请求次数
     *
     * 默认 5 秒内限制 10 次访问
     */
    int size() default 10;

    /**
     * 时间限制
     *
     * 默认 5 秒内限制 10 次访问
     */
    long time() default 5;
}
