package club.gclmit.chaos.core.lang;

import club.gclmit.chaos.core.pojo.GirlFriend;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/16 7:40 下午
 * @version: V1.0
 * @since 1.8
 */
public class BuilderTest {

    public static void main(String[] args) {
        GirlFriend friend = Builder.of(GirlFriend::new)
                .with(GirlFriend::setAddress, "浦东").build();

        System.out.println(friend);
    }
}
