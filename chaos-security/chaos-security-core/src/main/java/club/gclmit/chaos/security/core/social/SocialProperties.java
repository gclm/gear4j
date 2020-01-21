package club.gclmit.chaos.security.core.social;

/**
 * <p>
 *  social Spring Boot 2.x 移除文件
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/6 4:17 下午
 * @version: V1.0
 * @since 1.8
 */
public abstract class SocialProperties {

    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

}