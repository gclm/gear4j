package club.gclmit.chaos.security.core.social.qq.connet;

import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * <p>
 * 自定义实现 QQ OAuth2 部分流程
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/7 9:35 上午
 * @version: V1.0
 * @since 1.8
 */
public class QQOAuth2Template  extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        Logger.info(LoggerServer.SPRING_SECURITY,"获取 accessToken 的响应:{}",responseStr);

        /**
         * 分割字符串，获取  accessToken、expiresIn、refreshToken
         */
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        String expiresIn = StringUtils.substringAfterLast(items[1], "=");
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        //public AccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        return new AccessGrant(accessToken,"",refreshToken,Long.valueOf(expiresIn));
    }

    /**
     *  添加StringHttpMessageConverter 支持
     *
     * @author gclm
     * @date 2020/1/7 11:40 上午
     * @return: org.springframework.web.client.RestTemplate
     * @throws
     */
    @Override
    protected RestTemplate createRestTemplate() {

        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
