package club.gclmit.chaos.json;

import java.util.Set;

/**
 * 映射结构的只读数据集
 *
 * @author gclm
 */
public interface JsonObject extends Json {

    /**
     * 判断是否存在 key
     *
     * @param key 键名
     * @return 是否有该键
     */
    boolean has(String key);

    /**
     * 获取 JsonObject
     *
     * @param key 键名
     * @return 子 JsonObject
     */
    JsonObject getJsonObject(String key);

    /**
     * 获取 JsonArray
     *
     * @param key 键名
     * @return 子 JsonArray
     */
    JsonArray getJsonArray(String key);

    /**
     * 获取 boolean
     *
     * @param key 键名
     * @return boolean 值
     */
    boolean getBoolean(String key);

    /**
     * 获取 int
     *
     * @param key 键名
     * @return int 值
     */
    int getInt(String key);

    /**
     * 获取 long
     *
     * @param key 键名
     * @return long 值
     */
    long getLong(String key);

    /**
     * 获取 float
     *
     * @param key 键名
     * @return float 值
     */
    float getFloat(String key);

    /**
     * 获取 double
     *
     * @param key 键名
     * @return double 值
     */
    double getDouble(String key);

    /**
     * 获取 String
     *
     * @param key 键名
     * @return String 值
     */
    String getString(String key);

    /**
     * 获取 JSON 的键集合
     *
     * @return JSON 的键集合
     */
    Set<String> keySet();
}
