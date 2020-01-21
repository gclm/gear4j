package club.gclmit.chaos.security.core.social.qq.connet;

import club.gclmit.chaos.security.core.social.qq.api.QQ;
import club.gclmit.chaos.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * <p>
 *
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/6 2:34 下午
 * @version: V1.0
 * @since 1.8
 */
public class QQAdapter implements ApiAdapter<QQ> {

    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setDisplayName(userInfo.getNickname());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        /**
         *  qq 不存在时间线
         */
    }
}
