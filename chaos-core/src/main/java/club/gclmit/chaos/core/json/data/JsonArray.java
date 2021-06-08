package club.gclmit.chaos.core.json.data;

/**
 * 列表结构的只读数据集
 *
 * @author gclm
 */
public interface JsonArray extends Json {

    /**
     * 获取 JsonObject
     * @param index 元素下标
     * @return 子 JsonObject
     */
    JsonObject getJsonObject(int index);

    /**
     * 获取 JsonArray
     * @param index 元素下标
     * @return 子 JsonArray
     */
    JsonArray getJsonArray(int index);

    /**
     * 获取 boolean
     * @param index 元素下标
     * @return boolean 值
     */
    boolean getBoolean(int index);

    /**
     * 获取 int
     * @param index 元素下标
     * @return int 值
     */
    int getInt(int index);

    /**
     * 获取 long
     * @param index 元素下标
     * @return long 值
     */
    long getLong(int index);

    /**
     * 获取 float
     * @param index 元素下标
     * @return float 值
     */
    float getFloat(int index);

    /**
     * 获取 double
     * @param index 元素下标
     * @return double 值
     */
    double getDouble(int index);

    /**
     * 获取 String
     * @param index 元素下标
     * @return String 值
     */
    String getString(int index);

}

