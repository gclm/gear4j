package club.gclmit.chaos.core.util;

import java.util.*;

/**
 * <p>
 *  集合工具类封装
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/14 7:56 下午
 * @version: V1.0
 * @since 1.8
 */
public class CollectionUtils {

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptySet()}
     *
     * @param <T> 集合元素类型
     * @param set 提供的集合，可能为null
     * @return 原集合，若为null返回空集合
     * @since 4.6.3
     */
    public static <T> Set<T> emptyIfNull(Set<T> set) {
        return (null == set) ? Collections.emptySet() : set;
    }

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptyList()}
     *
     * @param <T> 集合元素类型
     * @param set 提供的集合，可能为null
     * @return 原集合，若为null返回空集合
     * @since 4.6.3
     */
    public static <T> List<T> emptyIfNull(List<T> set) {
        return (null == set) ? Collections.emptyList() : set;
    }


    // ---------------------------------------------------------------------- isEmpty


}
