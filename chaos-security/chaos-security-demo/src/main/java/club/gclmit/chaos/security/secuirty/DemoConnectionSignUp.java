package club.gclmit.chaos.security.secuirty;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 自动注册组件
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/7 9:11 下午
 * @version: V1.0
 * @since 1.8
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        /**
         * 根据社交用户信息默认创建用户并返回用户唯一标识
         */
        return connection.getKey().getProviderUserId();
    }
}
