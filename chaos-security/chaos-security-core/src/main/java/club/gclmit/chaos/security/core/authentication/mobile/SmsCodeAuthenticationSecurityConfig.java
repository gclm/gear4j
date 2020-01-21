package club.gclmit.chaos.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 短信权限配置
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/15 3:47 下午
 * @version: V1.0
 * @since 1.8
 */
@Component
public class SmsCodeAuthenticationSecurityConfig  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    private AuthenticationFailureHandler chaosAuthenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler chaosAuthenticationSuccessHandler;

    @Autowired
    private UserDetailsService chaosUserDetailsService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
      SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
      smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
      smsCodeAuthenticationFilter.setAuthenticationFailureHandler(chaosAuthenticationFailureHandler);
      smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(chaosAuthenticationSuccessHandler);


      SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
      smsCodeAuthenticationProvider.setUserDetailsService(chaosUserDetailsService);

      http.authenticationProvider(smsCodeAuthenticationProvider)
              .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }



}
