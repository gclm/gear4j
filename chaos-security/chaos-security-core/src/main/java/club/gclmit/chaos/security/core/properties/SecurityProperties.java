package club.gclmit.chaos.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *  security 配置工具类
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/6 10:04 上午
 * @version: V1.0
 * @since 1.8
 */
@ConfigurationProperties(prefix = "chaos.security")
public class SecurityProperties {

    /**
     *  浏览器相关的配置
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     *  验证码
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     *  第三方登陆
     */
    private SocialProperties social = new SocialProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }
}
