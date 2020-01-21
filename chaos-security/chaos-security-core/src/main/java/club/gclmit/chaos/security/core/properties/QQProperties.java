package club.gclmit.chaos.security.core.properties;

import club.gclmit.chaos.security.core.social.SocialProperties;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/6 4:04 下午
 * @version: V1.0
 * @since 1.8
 */
public class QQProperties extends SocialProperties {

    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}
