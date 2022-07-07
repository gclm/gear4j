package club.gclmit.gear4j.safe.core;

import java.util.Collection;

import club.gclmit.gear4j.safe.handler.SqlHandler;
import club.gclmit.gear4j.safe.handler.XssHandler;

/**
 * 安全规则
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/5 09:59
 * @since jdk11
 */
public class SafeRules {

    /**
     * 判断是不是注入xss和sql
     *
     * @param text 效验字符
     * @return {@link boolean} true
     */
    public static boolean isInjection(String text) {
        return isXssInjection(text) || isSqlInjection(text);
    }

    /**
     * 判断是不是注入xss和sql
     *
     * @param params 效验字符集合
     * @return {@link boolean} true
     */
    public static boolean isInjection(Collection<?> params) {
        boolean flag = false;
        for (Object param : params) {
            if (param instanceof String && isInjection((String)param)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断xss 是否注入
     *
     * @param text 效验字符
     * @return {@link boolean} true 是注入
     */
    public static boolean isXssInjection(String text) {
        return XssHandler.isXssInjection(text);
    }

    /**
     * 判断SQL是否注入 <br>
     * 1. 判断是否为空，为空直接通过 2. 不为空，则进行正则判断
     *
     * @param text 效验字符
     * @return {@link boolean} true 是注入
     */
    public static boolean isSqlInjection(String text) {
        return SqlHandler.isSqlInjection(text);
    }
}
