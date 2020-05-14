package club.gclmit.chaos.core.collection;

import java.util.List;

/**
 * <p>
 * List 集合工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/14 8:17 下午
 * @version: V1.0
 * @since 1.8
 */
public class ListUtils {

    /**
     * 判断List是否为空
     *
     * @param list
     * @return 是否为true
     */
    public static boolean isEmpty(List list) {
        return list.isEmpty() || list.size() == 0;
    }

    /**
     * 判断List是否不为空
     *
     * @param list
     * @return 是否为true
     */
    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

}
