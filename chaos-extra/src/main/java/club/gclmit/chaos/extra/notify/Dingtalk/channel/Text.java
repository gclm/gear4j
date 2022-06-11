package club.gclmit.chaos.extra.notify.Dingtalk.channel;

import club.gclmit.chaos.extra.notify.Dingtalk.DingtalkBot;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Text 消息
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/6 14:00
 * @since jdk11
 */
public class Text extends DingtalkChannel {

    /**
     * 消息内容。
     */
    private String content;

    public Text() {}

    public Text(DingtalkBot bot) {
        this.dingtalkBot = bot;
    }

    public Text content(String content) {
        this.content = content;
        return this;
    }

    public Text userIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }

    public Text mobiles(List<String> mobiles) {
        this.mobiles = mobiles;
        return this;
    }

    public Text atAll(boolean atAll) {
        this.atAll = atAll;
        return this;
    }

    public boolean send() {
        JSONObject at = new JSONObject();
        at.put("isAtAll", atAll);
        at.put("atMobiles", mobiles);
        at.put("atUserIds", userIds);
        JSONObject text = new JSONObject();
        text.put("content", content);

        JSONObject object = new JSONObject();
        object.put("msgtype", "text");
        object.put("at", at);
        object.put("text", text);
        String content = object.toJSONString();
        return dingtalkBot.send(content);
    }
}
