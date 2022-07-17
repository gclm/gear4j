package club.gclmit.gear4j.redis.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 系统全局Cache接口，具体缓存方式需要实现该接口
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/6 11:06
 * @since jdk11
 */
public interface GlobalCache {

    /**
     * 指定缓存失效时间
     *
     * @param key 键
     * @param timeout 时间(秒)
     */
    void setExpire(String key, Long timeout, TimeUnit unit);

    /**
     * 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    Long getExpire(String key);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    Boolean hasKey(String key);

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    void remove(String... key);

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    Object getValue(String key);

    /**
     * 普通缓存放入
     *
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    Boolean cacheValue(String key, Object value);

    /**
     * 普通缓存放入并设置时间
     *
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @param unit 时间单位
     * @return true成功 false 失败
     */
    Boolean cacheValue(String key, Object value, Long time, TimeUnit unit);

    /**
     * 递增
     *
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return {@literal null}
     */
    Long incrValue(String key, Long delta);

    /**
     * 递减
     *
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return {@literal null}
     */
    Long decrValue(String key, Long delta);

    // ============================hash=============================
    /**
     * HashGet
     *
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    Object getHash(String key, String item);

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    Map<Object, Object> getHash(String key);

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    Boolean cacheHash(String key, Map<String, Object> map);

    /**
     * HashSet 并设置时间
     *
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    Boolean cacheHash(String key, Map<String, Object> map, Long time, TimeUnit unit);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    Boolean cacheHash(String key, String item, Object value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    Boolean cacheHash(String key, String item, Object value, Long time, TimeUnit unit);

    /**
     * 删除hash表中的值
     *
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    void removeHash(String key, Object... item);

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    Boolean hasHashKey(String key, String item);

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return {@literal null}
     */
    double incrHash(String key, String item, double by);

    /**
     * hash递减
     *
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return {@literal null}
     */
    double decrHash(String key, String item, double by);

    // ============================set=============================

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return set缓存的长度
     */
    Long getSetSize(String key);

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return set值
     */
    Set<Object> getSet(String key);

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    Boolean hasSetKey(String key, Object value);

    /**
     * 将数据放入set缓存
     *
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    Long cacheSet(String key, Object... values);

    /**
     * 将set数据放入缓存
     *
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    Long cacheSet(String key, Long time, TimeUnit unit, Object... values);

    /**
     * 移除值为value的
     *
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    Long removeSet(String key, Object... values);

    // ============================list=============================

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return 缓存长度
     */
    Long getListSize(String key);

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     * @param start 开始
     * @param end 结束 0 到 -1代表所有值
     * @return 缓存list
     */
    List<Object> getList(String key, Long start, Long end);

    /**
     * 通过索引 获取list中的值
     *
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 索引值
     */
    Object getList(String key, Long index);

    /**
     * 将list放入缓存
     *
     * @param key 键
     * @param value 值
     * @return 插入状态
     */
    Boolean cacheList(String key, Object value);

    /**
     * 将list放入缓存
     *
     * @param key 键
     * @param value 值
     * @param time 过期时间
     * @param unit 时间单位
     * @return 插入状态
     */
    Boolean cacheList(String key, Object value, Long time, TimeUnit unit);

    /**
     * 将list放入缓存
     *
     * @param key 键
     * @param value 值
     * @return 插入状态
     */
    Boolean cacheList(String key, List<Object> value);

    /**
     * 将list放入缓存
     *
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @param unit 时间单位
     * @return 修改状态
     */
    Boolean cacheList(String key, List<Object> value, Long time, TimeUnit unit);

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return 修改状态
     */
    Boolean updateList(String key, Long index, Object value);

    /**
     * 移除N个值为value
     *
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    Long removeList(String key, Long count, Object value);

    /**
     * 从redis集合中移除[start,end]之间的元素
     *
     * @param key 位置
     * @param start 开始元素
     * @param end 结束元素
     */
    void removeList(String key, Long start, Long end);

}
