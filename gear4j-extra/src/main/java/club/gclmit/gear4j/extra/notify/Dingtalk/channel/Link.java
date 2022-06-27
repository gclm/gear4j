package club.gclmit.gear4j.extra.notify.Dingtalk.channel;

import com.alibaba.fastjson.JSONObject;

import club.gclmit.gear4j.extra.notify.Dingtalk.DingtalkBot;

/**
 * Link 消息
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/6 14:00
 * @since jdk11
 */
public class Link {

    /**
     * 消息内容。如果太长只会部分展示。
     */
    private String text;

    /**
     * 消息标题。
     */
    private String title;

    /**
     * 图片URL。
     */
    private String picUrl;

    /**
     * 点击消息跳转的URL，打开方式如下： 1.移动端：在钉钉客户端内打开 2.PC端：默认侧边栏打开，希望在外部浏览器打开，请参考
     *
     * @link <a href="https://open.dingtalk.com/document/orgapp-server/message-link-description">消息链接说明</a>
     */
    private String messageUrl;

    DingtalkBot dingtalkBot;

    public Link() {}

    public Link(DingtalkBot bot) {
        dingtalkBot = bot;
    }

    public Link text(String text) {
        this.text = text;
        return this;
    }

    public Link title(String title) {
        this.title = title;
        return this;
    }

    public Link picUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public Link messageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
        return this;
    }

    public boolean send() {
        JSONObject link = new JSONObject();
        link.put("text", text);
        link.put("title", title);
        link.put("picUrl", picUrl);
        link.put("messageUrl", messageUrl);
        JSONObject object = new JSONObject();
        object.put("msgtype", "link");
        object.put("link", link);
        String content = object.toJSONString();
        return dingtalkBot.send(content);
    }
}
