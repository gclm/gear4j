package club.gclmit.chaos.security.core.social;

import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import club.gclmit.chaos.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;
import javax.sql.DataSource;

/**
 * <p>
 * Spring Social config
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/6 3:12 下午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    /**
     * 注册 数据源
     * @author gclm
     * @param: connectionFactoryLocator
     * @date 2020/1/7 2:11 下午
     * @return: org.springframework.social.connect.UsersConnectionRepository
     * @throws
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix(securityProperties.getSocial().getTablePrefix());
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 注册自己实现的 chaoSpringSocialConfigurer 解决 社交登陆前缀的问题
     * @author gclm
     * @date 2020/1/7 2:07 下午
     * @return: org.springframework.social.security.SpringSocialConfigurer
     * @throws
     */
    @Bean
    public SpringSocialConfigurer chaoSpringSocialConfigurer() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        Logger.info(LoggerServer.SPRING_SECURITY,"社交登陆前缀:[{}]",filterProcessesUrl);
        ChaosSpringSocialConfigurer configurer = new ChaosSpringSocialConfigurer(filterProcessesUrl);
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return configurer;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    /**
     *  解决 Spring Social 和 业务系统的通信问题
     *
     * @author gclm
     * @param: connectionFactoryLocator
     * @date 2020/1/7 2:13 下午
     * @return: org.springframework.social.connect.web.ProviderSignInUtils
     * @throws
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }

    /**
     *  创建 Connect Controller
     *  https://docs.spring.io/spring-social/docs/1.1.x-SNAPSHOT/reference/htmlsingle/#creating-connections-with-connectcontroller
     *
     * @author gclm
     * @param: connectionFactoryLocator
     * @param: connectionRepository
     * @date 2020/1/21 5:25 下午
     * @return: org.springframework.social.connect.web.ConnectController
     * @throws
     */
    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }
}
