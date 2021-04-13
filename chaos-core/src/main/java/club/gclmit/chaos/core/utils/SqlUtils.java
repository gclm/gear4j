package club.gclmit.chaos.core.utils;

import club.gclmit.chaos.core.exception.ChaosException;
import lombok.experimental.UtilityClass;

/**
 * SQL 常用工具类
 *
 * @author gclm
 */
@UtilityClass
public class SqlUtils {

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 判断数据是否修改成功
     *
     * @param result 处理结果次数
     * @return boolean
     * @author gclm
     */
    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new ChaosException("参数不符合规范，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }
}
