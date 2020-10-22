package club.gclmit.chaos.core.collection;

import java.util.List;

/**
 * <p>
 * List 集合工具类
 * </p>
 *
 * @author gclm
 */
public class ListUtils {

    /**
     * 判断List是否为空
     *
     * @param list 效验集合
     * @return 是否为true
     */
    public static boolean isEmpty(List list) {
        return list.isEmpty() || list.size() == 0;
    }

    /**
     * 判断List是否不为空
     *
     * @param list 效验集合
     * @return 是否为true
     */
    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    /**
     * to String[]
     *
     * @param list 需要转换的集合
     * @author gclm
     * @return: java.lang.String[]
     */
    public static String[] toArray(List list) {
        String[] tables = new String[list.size()];
        return (String[]) list.toArray(tables);
    }

}
