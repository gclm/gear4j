package club.gclmit.chaos.security.core.authentication;

import club.gclmit.chaos.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * <p>
 *  账号密码登录相关的权限配置
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/15 5:00 下午
 * @version: V1.0
 * @since 1.8
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler chaosAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler chaosAuthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
            http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(chaosAuthenticationSuccessHandler)
                .failureHandler(chaosAuthenticationFailureHandler);
    }
}
