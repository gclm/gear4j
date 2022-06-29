package club.gclmit.gear4j.extra.notify.Dingtalk.channel;


import java.util.List;

import club.gclmit.gear4j.extra.notify.Dingtalk.DingtalkBot;

/**
 * 封装钉钉消息通道
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/6 14:00
 * @since jdk11
 */
public class DingtalkChannel {

    /**
     * 被@人的用户userid。
     */
    List<String> userIds;

    /**
     * 被@人的手机号。
     */
    List<String> mobiles;

    /**
     * 是否@所有人。
     */
    boolean atAll;

    DingtalkBot dingtalkBot;

}
