package club.gclmit.chaos.core.util;

/**
 * <p>
 * sql 工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/14 8:30 下午
 * @version: V1.0
 * @since 1.8
 */
public class SqlUtils {

    /**
     * 仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !RegUtils.isMatch(SQL_PATTERN, value)) {
            return StringUtils.EMPTY;
        }
        return value;
    }
}
