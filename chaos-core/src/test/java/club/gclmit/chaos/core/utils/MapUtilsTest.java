package club.gclmit.chaos.core.utils;

import club.gclmit.chaos.core.pojo.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * MapUtils 测试
 *
 * @author gclm
 */
public class MapUtilsTest {

    /**
     * 配置静态 MAP
     */
    public static Map<String, Object> MAP = new HashMap<>(10);

    @Test
    public void mapToObject() throws Exception {
        MAP.put("id", 1111);
        MAP.put("name", "str");
        User user = (User) MapUtils.mapToObject(MAP, User.class);
        System.out.println(user);
    }

    @Test
    public void mapToObject2() throws Exception {
        String[] strArray = {"111"};
        MAP.put("id", 1111);
        MAP.put("name", strArray);
        MAP.put("role", strArray);
        User user = (User) MapUtils.mapToObject(MAP, User.class);
        System.out.println(user);
    }

    @Test
    public void objectToMap() throws Exception {
        User user = User.builder().id("1111").name("str").build();
        Map map = MapUtils.objectToMap(user);
        for (Object key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }
    }

}
