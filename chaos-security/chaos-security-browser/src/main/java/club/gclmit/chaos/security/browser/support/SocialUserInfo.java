package club.gclmit.chaos.security.browser.support;

/**
 * <p>
 * 封装的 SocialUserInfo 对象
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/7 2:02 下午
 * @version: V1.0
 * @since 1.8
 */
public class SocialUserInfo {

    /**
     * 服务商 id
     */
    private String providerId;

    /**
     * 服务商 用户id
     */
    private String providerUserId;

    /**
     *  用户昵称
     */
    private String nickname;

    /**
     *  用户头像
     */
    private String headIng;

    /**
     *  用户邮箱
     */
    private String email;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadIng() {
        return headIng;
    }

    public void setHeadIng(String headIng) {
        this.headIng = headIng;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
