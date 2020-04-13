package club.gclmit.chaos.core.helper;

import java.util.Collection;

/**
 * <p>
 * 集合工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/13 10:25 上午
 * @version: V1.0
 * @since 1.8
 */
public class CollectionHelper {

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return false == isEmpty(collection);
    }

}
