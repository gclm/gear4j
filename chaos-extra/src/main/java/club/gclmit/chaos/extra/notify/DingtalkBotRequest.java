package club.gclmit.chaos.extra.notify;

/**
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/5/29 16:43
 * @since jdk11
 */
public class DingtalkBotRequest {

	public static final String DINGTALK_API = "https://oapi.dingtalk.com/robot/send?access_token=%s&timestamp=%s&sign=%s";

	/**
	 * WebHook地址
	 */
	private String webhook;

	/**
	 * secret
	 */
	private String secret;

	public String getWebhook() {
		return webhook;
	}

	public void setWebhook(String webhook) {
		this.webhook = webhook;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setText(String text) {

	}

	public void setMarkdown(String title, String markdown) {
	}

}
