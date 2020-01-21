package club.gclmit.chaos.security.core.social.qq.connet;

import club.gclmit.chaos.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/6 2:56 下午
 * @version: V1.0
 * @since 1.8
 */
public class QQConnectFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"
     * @param appid           qq openId
     * @param appSecret      qq appSecret
     */
    public QQConnectFactory(String providerId, String appid, String appSecret) {
        super(providerId, new QQServiceProvider(appid,appSecret), new QQAdapter());
    }
}
