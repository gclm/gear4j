package club.gclmit.chaos.json;

/**
 * JSON 公共父类
 *
 * @author gclm
 */
public interface Json {

    /**
     * 获取 json 大小
     *
     * @return 集合内直接子元素的数量
     */
    int size();

    /**
     * 判断 json 数组/对象是否为空
     *
     * @return 数据集是否为空
     */
    boolean isEmpty();

}
