package club.gclmit.chaos.extra.notify.Dingtalk.channel;

import club.gclmit.chaos.extra.notify.Dingtalk.DingtalkBot;
import com.alibaba.fastjson.JSONObject;
import java.util.List;

/**
 * ActionCard 消息
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/6 14:01
 * @since jdk11
 */
public class ActionCard {

    /**
     * 首屏会话透出的展示内容。
     */
    private String title;

    /**
     * markdown格式的消息。
     */
    private String text;
    /**
     * 0：按钮竖直排列 1：按钮横向排列
     */
    private String btnOrientation = "0";

    /**
     * 单个按钮的标题。
     *
     * 注意 设置此项和singleURL后，btns无效。
     */
    private String singleTitle;

    /**
     * 点击消息跳转的URL，打开方式如下： 1.移动端：在钉钉客户端内打开 2.PC端：默认侧边栏打开，希望在外部浏览器打开，请参考
     *
     * @link <a href="https://open.dingtalk.com/document/orgapp-server/message-link-description">消息链接说明</a>
     */
    private String singleURL;

    private List<Button> btns;

    private DingtalkBot dingtalkBot;

    public ActionCard() {}

    public ActionCard(DingtalkBot bot) {
        dingtalkBot = bot;
    }

    public ActionCard title(String title) {
        this.title = title;
        return this;
    }

    public ActionCard text(String text) {
        this.text = text;
        return this;
    }

    public ActionCard btns(List<Button> btns) {
        this.btns = btns;
        return this;
    }

    public ActionCard btnOrientation(String btnOrientation) {
        this.btnOrientation = btnOrientation;
        return this;
    }

    public ActionCard singleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
        return this;
    }

    public ActionCard singleURL(String singleURL) {
        this.singleURL = singleURL;
        return this;
    }

    public boolean send() {
        JSONObject actionCard = new JSONObject();
        actionCard.put("title", title);
        actionCard.put("text", text);
        actionCard.put("btnOrientation", btnOrientation);
        actionCard.put("singleTitle", singleTitle);
        actionCard.put("singleURL", singleURL);
        actionCard.put("btns", btns);

        JSONObject object = new JSONObject();
        object.put("msgtype", "actionCard");
        object.put("actionCard", actionCard);

        String content = object.toJSONString();
        return dingtalkBot.send(content);
    }
}
