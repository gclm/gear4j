package club.gclmit.chaos.core.util;

/**
 * SQL 常用工具类
 *
 * @author gclm
 */
public class SQLUtils {

    /**
     *  判断数据是否修改成功
     *
     * @author gclm
     * @param result 处理结果次数
     * @return boolean
     */
    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }
}
