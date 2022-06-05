package club.gclmit.chaos.extra.notify.Dingtalk;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * DingtalkChannel
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/5/29 17:16
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

    static class Text extends DingtalkChannel {

        /**
         * 消息内容。
         */
        private String content;

        public Text() {}

        public Text(DingtalkBot bot) {
            dingtalkBot = bot;
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

    static class Link {
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

    static class button {
        private String title;
        private String actionURL;

        public button(String title, String actionURL) {
            this.title = title;
            this.actionURL = actionURL;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActionURL() {
            return actionURL;
        }

        public void setActionURL(String actionURL) {
            this.actionURL = actionURL;
        }
    }

    static class ActionCard {

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

        private List<button> btns;

        DingtalkBot dingtalkBot;

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

        public ActionCard btns(List<button> btns) {
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

    static class Markdown extends DingtalkChannel {

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
            dingtalkBot = bot;
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
}
