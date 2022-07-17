package club.gclmit.gear4j.extra.notify.dingtalk;

import java.net.URLEncoder;

import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.OkHttps;

import club.gclmit.gear4j.core.utils.SecureUtils;
import club.gclmit.gear4j.core.utils.StringUtils;
import cn.hutool.core.util.CharsetUtil;

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
        String body = result.getBody().toString();
        System.out.println(StringUtils.format("钉钉消息发送({}): -->{}", result.getStatus(), body));
        if (HttpStatus.OK.value() == result.getStatus()) {
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
