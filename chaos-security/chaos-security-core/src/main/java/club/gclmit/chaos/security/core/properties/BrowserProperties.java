package club.gclmit.chaos.security.core.properties;

/**
 * <p>
 * 浏览器相关参数封装
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/6 10:09 上午
 * @version: V1.0
 * @since 1.8
 */
public class BrowserProperties {
    
    /**
     * 默认注册页面
     */
    private String signUpUrl = "/chaos-signUp.html";

    /**
     *  登录页面默认地址
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    /**
     *  登录相应类型枚举
     */
    private LoginResponseType LoginType = LoginResponseType.JSON;

    /**
     * 记住我的时间
     */
    private int rememberMeSeconds = 3600;


    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginResponseType getLoginType() {
        return LoginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        LoginType = loginType;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }
}
