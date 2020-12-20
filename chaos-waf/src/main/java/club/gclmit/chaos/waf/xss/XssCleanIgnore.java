package club.gclmit.chaos.web.filter.xss;

import java.lang.annotation.*;

/**
 * 忽略 xss
 *
 * @author L.cm
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XssCleanIgnore {
}
