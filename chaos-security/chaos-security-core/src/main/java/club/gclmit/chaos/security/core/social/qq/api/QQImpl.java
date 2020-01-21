package club.gclmit.chaos.security.core.social.qq.api;

import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/20 7:42 下午
 * @version: V1.0
 * @since 1.8
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String GET_OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String GET_USER_INFO_URL = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;
        String url = String.format(GET_OPEN_ID_URL, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        this.openId = StringUtils.substringBetween(result,"openid\":\"","\"}");
        Logger.info(LoggerServer.SPRING_SECURITY,"openid 响应:[{}]\topenId:[{}]",result.trim(),this.openId);
    }

    @Override
    public QQUserInfo getUserInfo(){
        String url = String.format(GET_USER_INFO_URL, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        Logger.info(LoggerServer.SPRING_SECURITY,"获取 QQUserInfo 响应结果：[{}]",result);
        QQUserInfo qqUserInfo = null;
        try {
            qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return  qqUserInfo;
        } catch (IOException e) {
           throw new RuntimeException("获取用户信息失败",e);
        }
    }
}
