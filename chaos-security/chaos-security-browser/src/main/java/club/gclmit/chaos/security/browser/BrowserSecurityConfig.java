package club.gclmit.chaos.security.browser;

import club.gclmit.chaos.security.core.authentication.AbstractChannelSecurityConfig;
import club.gclmit.chaos.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import club.gclmit.chaos.security.core.properties.SecurityConstants;
import club.gclmit.chaos.security.core.properties.SecurityProperties;
import club.gclmit.chaos.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;
import javax.sql.DataSource;

/**
 * <p>
 * Spring Security 配置
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/25 9:27
 * @version: V1.0
 * @since 1.8
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService chaosUserDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig  validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer chaosSocialConfigurerConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            .apply(chaosSocialConfigurerConfig)
                .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(chaosUserDetailsService)
                .and()
             .authorizeRequests()
                 .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX +"/*",
                        securityProperties.getBrowser().getSignUpUrl(),
                        "/user/regist")
                    .permitAll()
                 .anyRequest()
                 .authenticated()
                 .and()
            . csrf().disable();
    }

    /**
     *  采用新的加密格式
     *
     * @author gclm
     * @date 2020/1/7 3:33 下午
     * @return: org.springframework.security.crypto.password.PasswordEncoder
     * @throws
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        /**
         * 自动建表，若存在表则报错
         * tokenRepository.setCreateTableOnStartup(true);
         */
        return tokenRepository;
    }
}