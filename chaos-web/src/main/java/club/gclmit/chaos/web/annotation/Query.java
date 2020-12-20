package club.gclmit.chaos.web.annotation;

import java.lang.annotation.*;

/**
 * 快速查询
 *
 * @author gclm
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {

   Class clazz();
}