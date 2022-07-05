package club.gclmit.gear4j.safe.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import club.gclmit.gear4j.core.lang.Log;
import club.gclmit.gear4j.core.lang.LoggerProvider;
import club.gclmit.gear4j.core.utils.StringUtils;

/**
 * SQL 注入处理器
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/5 13:24
 * @since jdk11
 */
public class SqlHandler {

    public static final String REG = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
        + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    /**
     * 正则判断，忽略大小写
     */
    public static Pattern SQL_PATTERN = Pattern.compile(REG, Pattern.CASE_INSENSITIVE);

    /**
     * 判断SQL是否注入 <br>
     * 1. 判断是否为空，为空直接通过 2. 不为空，则进行正则判断
     *
     * @param text 效验字符
     * @return {@link boolean} true 是注入
     */
    public static boolean isSqlInjection(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        Matcher matcher = SQL_PATTERN.matcher(text);
        if (matcher.find()) {
            Log.info(LoggerProvider.GEAR4J, "SqlInjection提醒你参数存在非法字符，请确认：[{}]", text);
            return true;
        }
        return false;
    }
}
