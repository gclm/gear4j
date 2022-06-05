package club.gclmit.chaos.extra.notify.Dingtalk;

import club.gclmit.chaos.core.utils.SecureUtils;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpStatus;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.OkHttps;

import java.net.URLEncoder;
import java.util.List;

/**
 * 钉钉机器人
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/5/28 15:59
 * @since jdk11
 */
public class DingtalkBot {

    public static final String SERVER_API = "https://oapi.dingtalk.com/robot/send?access_token=%s&timestamp=%s&sign=%s";
    public static final String SERVER_HOST = "https://oapi.dingtalk.com/robot/send?access_token=";

    private String webhook;
    private String secret;

    private static DingtalkBot dingtalkBot;

    public DingtalkBot() {}

    public DingtalkBot(String webhook, String secret) {
        this.webhook = webhook;
        this.secret = secret;
    }

    public static DingtalkBot builder(String secret, String webhook) {
        dingtalkBot = new DingtalkBot(webhook, secret);
        return dingtalkBot;
    }

    public static DingtalkBot builder() {
        dingtalkBot = new DingtalkBot();
        return dingtalkBot;
    }

    public DingtalkBot webhook(String webhook) {
        this.webhook = webhook;
        return this;
    }

    public DingtalkBot secret(String secret) {
        this.secret = secret;
        return this;
    }

    public DingtalkChannel.Link link() {
        return new DingtalkChannel.Link(dingtalkBot);
    }

    public DingtalkChannel.Markdown markdown() {
        return new DingtalkChannel.Markdown(dingtalkBot);
    }

    public DingtalkChannel.Text text() {
        return new DingtalkChannel.Text(dingtalkBot);
    }

    public DingtalkChannel.ActionCard actionCard() {
        return new DingtalkChannel.ActionCard(dingtalkBot);
    }

    public boolean send(String content) {
        long millis = System.currentTimeMillis();
        String accessToken = webhook.replace(SERVER_HOST, "");
        String url = String.format(SERVER_API, accessToken, millis, getSign(millis, secret));
        HttpResult result = OkHttps.async(url).setBodyPara(content).bodyType(OkHttps.JSON).post().getResult();
        System.out.println(result.getBody().toString());
        return HttpStatus.HTTP_OK == result.getStatus();
    }

    public static String getSign(Long timestamp, String secret) {
        String stringToSign = timestamp + "\n" + secret;
        String base64 = SecureUtils.hmacSha256(secret).digestBase64(stringToSign, false);
        return URLEncoder.encode(base64, CharsetUtil.CHARSET_UTF_8);
    }

    public static void main(String[] args) {
        String webhook =
            "https://oapi.dingtalk.com/robot/send?access_token=ba488c121edfcbdd74f29ca0024b95424f75f6cb8feab18b643c39d0663c2cd4";
        String secret = "SEC5bad10ba63a293ad88f2bc086dcfa63ebf10e6b6ac5885cd0dd3e559f538b3bd";
        DingtalkBot bot = new DingtalkBot(webhook, secret);

        boolean flag = DingtalkBot.builder().webhook(webhook).secret(secret).link()
            .text("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林").title("link 测试")
            .picUrl("https://image.coderlab.cn/preview/1500374025552195586").messageUrl("https://www.baidu.com").send();

        System.out.println(flag);

        System.out.println(DingtalkBot.builder().webhook(webhook).secret(secret).text().content("text 测试")
            .mobiles(List.of("17326039618")).send());

        System.out.println(DingtalkBot.builder().webhook(webhook).secret(secret).markdown()
            .mobiles(List.of("17326039618")).text("- xx  - yy  - zz").title("Markdown 测试").send());

        System.out.println(DingtalkBot.builder().webhook(webhook).secret(secret).actionCard()
            .title("我 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身")
            .text(
                "![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png) \\n\\n #### 乔布斯 20 年前想打造的苹果咖啡厅 \\n\\n Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划")
            .btns(List.of(new DingtalkChannel.button("内容不错", "https://www.dingtalk.com/"),
                new DingtalkChannel.button("不感兴趣", "https://www.dingtalk.com/")))
            .send());
    }
}
