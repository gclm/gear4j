package club.gclmit.gear4j.extra.notify.Dingtalk.channel;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import club.gclmit.gear4j.extra.notify.Dingtalk.DingtalkBot;

/**
 * Markdown 消息
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/6 14:00
 * @since jdk11
 */
public class Markdown extends DingtalkChannel {

    /**
     * 首屏会话透出的展示内容。
     */
    private String title;

    /**
     * markdown格式的消息。
     */
    private String text;

    public Markdown() {}

    public Markdown(DingtalkBot bot) {
        this.dingtalkBot = bot;
    }

    public Markdown userIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }

    public Markdown mobiles(List<String> mobiles) {
        this.mobiles = mobiles;
        return this;
    }

    public Markdown atAll(boolean atAll) {
        this.atAll = atAll;
        return this;
    }

    public Markdown title(String title) {
        this.title = title;
        return this;
    }

    public Markdown text(String text) {
        this.text = text;
        return this;
    }

    public boolean send() {
        JSONObject at = new JSONObject();
        at.put("isAtAll", atAll);
        at.put("atMobiles", mobiles);
        at.put("atUserIds", userIds);

        JSONObject markdown = new JSONObject();
        markdown.put("title", title);
        markdown.put("text", text);

        JSONObject object = new JSONObject();
        object.put("msgtype", "markdown");
        object.put("at", at);
        object.put("markdown", markdown);
        String content = object.toJSONString();
        return dingtalkBot.send(content);
    }
}
