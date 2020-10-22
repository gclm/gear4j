package club.gclmit.chaos.core.util;


import club.gclmit.chaos.core.lang.text.StringUtils;
import club.gclmit.chaos.core.pojo.User;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/16 6:39 下午
 * @version: V1.0
 * @since 1.8
 */
public class ObjectUtilsTest {

    public static void main(String[] args) {
       case1();
    }

    public static void case1(){
        User user = new User();
        user.setId("111");
        user.setName("test");
        System.out.println(StringUtils.toString(user));
    }

    public static void case2(){

    }
}
