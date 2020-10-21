package club.gclmit.chaos.core.collection;


import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.lang.text.StringUtils;

import java.util.*;

/**
 * <p>
 *  数组工具类
 * </p>
 * @author gclm
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

    /**
     * 去除集合中的空值
     * @author gclm
     * @param data 效验集合
     * @return  返回非空集合
     */
    public static String[] removeIfNull(String[] data){
        Assert.isTrue(isNotEmpty(data),"集合不为空");
        List<String> result = new ArrayList<>(data.length);
        for (String s : data){
            if (StringUtils.isNotBlank(s)){
                result.add(s);
            }
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * toString
     * @author gclm
     * @param data   集合
     * @return java.lang.String
     */
    public static String toString(Object[] data){
        return Arrays.toString(data).replace(",","");
    }

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
     * 如果给定集合为空，返回默认集合
     *
     * @param <T>               集合类型
     * @param <E>               集合元素类型
     * @param collection        集合
     * @param defaultCollection 默认数组
     * @return 非空（empty）的原集合或默认集合
     * @since 4.6.9
     */
    public static <T extends Collection<E>, E> T defaultIfEmpty(T collection, T defaultCollection) {
        return isEmpty(collection) ? defaultCollection : collection;
    }

    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }


    /**
     * Enumeration是否为空
     *
     * @param enumeration {@link Enumeration}
     * @return 是否为空
     */
    public static boolean isEmpty(Enumeration<?> enumeration) {
        return null == enumeration || false == enumeration.hasMoreElements();
    }

    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterable<?> iterable) {
        return null == iterable || isEmpty(iterable.iterator());
    }

    /**
     * Iterator是否为空
     *
     * @param terator terator对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterator<?> terator) {
        return null == terator || false == terator.hasNext();
    }


    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Enumeration是否为空
     *
     * @param enumeration {@link Enumeration}
     * @return 是否为空
     */
    public static boolean isNotEmpty(Enumeration<?> enumeration) {
        return !isEmpty(enumeration);
    }

    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     */
    public static boolean isNotEmpty(Iterable<?> iterable) {
        return !isEmpty(iterable);
    }

    /**
     * Iterator是否为空
     *
     * @param terator Iterator对象
     * @return 是否为空
     */
    public static boolean isNotEmpty(Iterator<?> terator) {
        return !isEmpty(terator);
    }

}