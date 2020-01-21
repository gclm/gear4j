package club.gclmit.chaos.security.core.social.qq.config;

import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import club.gclmit.chaos.security.core.social.qq.connet.QQConnectFactory;
import club.gclmit.chaos.security.core.properties.QQProperties;
import club.gclmit.chaos.security.core.properties.SecurityProperties;
import club.gclmit.chaos.security.core.social.SocialAutoConfigurerAdapter;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * <p>
 *  QQ 自动配置
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/6 4:28 下午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
@ConditionalOnProperty(prefix = "chaos.security.social.qq" , name = "app-id")
public class QQAutoConfig  extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        Logger.info(LoggerServer.SPRING_SECURITY,"读取的 QQ 配置参数为:[{}]",ReflectionToStringBuilder.toString(qqProperties, ToStringStyle.SHORT_PREFIX_STYLE));
        return new QQConnectFactory(qqProperties.getProviderId(),qqProperties.getAppId(),qqProperties.getAppSecret());
    }
}
