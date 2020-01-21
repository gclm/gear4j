package club.gclmit.chaos.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * <p>
 * 自定义 SpringSocialConfigurer 解决社交登陆前缀
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/6 5:20 下午
 * @version: V1.0
 * @since 1.8
 */
public class ChaosSpringSocialConfigurer extends SpringSocialConfigurer {

    /**
     * 社交登陆前缀的url
     */
    private String filterProcessesUrl;

    public ChaosSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
