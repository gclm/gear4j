package club.gclmit.chaos.bean;

import club.gclmit.chaos.bean.pojo.FormConvertUser;
import club.gclmit.chaos.bean.pojo.FormUser;
import club.gclmit.chaos.bean.pojo.ToConvertUser;
import club.gclmit.chaos.bean.pojo.ToUser;
import club.gclmit.chaos.bean.util.BeanUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean Utils 使用帮助
 *
 * @author gclm
 */
public class BeanUtilsTest {

    public static void copyToOne() {
        FormUser user = new FormUser();
        user.setId(10000L);
        user.setAge(24);
        user.setNickName("gclm");
        user.setEmail("1719982754@qq.com");
        user.setGender(1);
        user.setPassword("4e1b600b1fd579f47433b88e8d85291");
        user.setAvatar("https://avatars1.githubusercontent.com/u/27618687");
        user.setPhone("173********");

        ToUser toUser = BeanUtils.copy(user, ToUser.class);
        System.out.println(toUser);
    }

    public static void copyToConvert() {
        FormConvertUser user = new FormConvertUser();
        user.setId(10000L);
        user.setAge(24);
        user.setNickName("gclm");
        user.setEmail("1719982754@qq.com");
        user.setGender(1);
        user.setPassword("4e1b600b1fd579f47433b88e8d85291");
        user.setAvatar("https://avatars1.githubusercontent.com/u/27618687");
        user.setPhone("173********");
        user.setBirthday(LocalDateTime.now());

        ToConvertUser toUser = BeanUtils.copy(user, ToConvertUser.class);
        System.out.println(toUser);
    }

    public static void copyToList() {
        List<FormUser> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            FormUser user = new FormUser();
            user.setId(10000L + i);
            user.setAge(24 + i);
            user.setNickName("gclm -->" + i);
            user.setEmail("1719982754@qq.com");
            user.setGender(1);
            user.setPassword("4e1b600b1fd579f47433b88e8d85291");
            user.setAvatar("https://avatars1.githubusercontent.com/u/27618687");
            user.setPhone("173********");
            userList.add(user);
        }

        List<ToUser> toUsers = BeanUtils.copy(userList, ToUser.class);
        toUsers.forEach(System.out::println);
    }

    public static void copyToMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", 10000L);
        userMap.put("nickName", "gclm");
        userMap.put("age", 24);
        userMap.put("phone", "173********");
        userMap.put("email", "1719982754@qq.com");
        userMap.put("password", "4e1b600b1fd579f47433b88e8d85291");
        userMap.put("gender", 1);
        userMap.put("avatar", "https://avatars1.githubusercontent.com/u/27618687");

        ToUser user = BeanUtils.copy(userMap, ToUser.class);
        System.out.println(user);
    }


    public static void main(String[] args) {
        System.out.println("-----------copyToOne------------");
        copyToOne();
        System.out.println("-----------copyToConvert------------");
        copyToConvert();
        System.out.println("-----------copyToList------------");
        copyToList();
        System.out.println("-----------copyToMap------------");
        copyToMap();
    }
}
