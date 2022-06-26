package club.gclmit.chaos.extra.notify.Dingtalk;

import club.gclmit.chaos.core.utils.SecureUtils;
import club.gclmit.chaos.core.utils.StringUtils;
import club.gclmit.chaos.extra.notify.Dingtalk.channel.ActionCard;
import club.gclmit.chaos.extra.notify.Dingtalk.channel.Link;
import club.gclmit.chaos.extra.notify.Dingtalk.channel.Markdown;
import club.gclmit.chaos.extra.notify.Dingtalk.channel.Text;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.OkHttps;

import java.net.URLEncoder;

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

    public Link link() {
        return new Link(dingtalkBot);
    }

    public Markdown markdown() {
        return new Markdown(dingtalkBot);
    }

    public Text text() {
        return new Text(dingtalkBot);
    }

    public ActionCard actionCard() {
        return new ActionCard(dingtalkBot);
    }

    public boolean send(String content) {
        long millis = System.currentTimeMillis();
        String accessToken = webhook.replace(SERVER_HOST, "");
        String url = String.format(SERVER_API, accessToken, millis, getSign(millis, secret));
        HttpResult result = OkHttps.async(url).setBodyPara(content).bodyType(OkHttps.JSON).post().getResult();
        String body = result.getBody().toString();
        System.out.println(StringUtils.format("钉钉消息发送({}): -->{}", result.getStatus(), body));
        if (HttpStatus.HTTP_OK == result.getStatus()) {
            return 0 == JSONObject.parseObject(body).getInteger("errcode");
        }
        return false;
    }

    public static String getSign(Long timestamp, String secret) {
        String stringToSign = timestamp + "\n" + secret;
        String base64 = SecureUtils.hmacSha256(secret).digestBase64(stringToSign, false);
        return URLEncoder.encode(base64, CharsetUtil.CHARSET_UTF_8);
    }
}