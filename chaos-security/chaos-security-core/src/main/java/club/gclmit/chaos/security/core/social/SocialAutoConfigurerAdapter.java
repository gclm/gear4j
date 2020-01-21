package club.gclmit.chaos.security.core.social;

import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * <p>
 *  social Spring Boot 2.x 移除文件
 *  Base class for auto-configured {@link SocialConfigurerAdapter}s.
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/6 4:29 下午
 * @version: V1.0
 * @since 1.8
 */
public abstract class SocialAutoConfigurerAdapter extends SocialConfigurerAdapter {

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    protected abstract ConnectionFactory<?> createConnectionFactory();

}
