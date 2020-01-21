package club.gclmit.chaos.security.core.social.qq.connet;

import club.gclmit.chaos.security.core.social.qq.api.QQ;
import club.gclmit.chaos.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/21 3:04 下午
 * @version: V1.0
 * @since 1.8
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    /**
     * Step1：获取Authorization Code
     */
    public static final String AUTHORIZATION_CODE_URL = "https://graph.qq.com/oauth2.0/authorize";

    /**
     *   Step2：通过Authorization Code获取Access Token
     */
    public static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId,String appSecret) {
        super(new QQOAuth2Template(appId,appSecret,AUTHORIZATION_CODE_URL,ACCESS_TOKEN_URL));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
