package club.gclmit.chaos.security.secuirty;

import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  用户详情服务
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/4 5:01 下午
 * @version: V1.0
 * @since 1.8
 */
@Component("chaosUserDetailsService")
public class ChaosUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Logger.info(LoggerServer.SPRING_SECURITY, "登录用户名:[{}]", username);
        return buildUser(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        Logger.info(LoggerServer.SPRING_SECURITY,"设计登陆用户Id:[{}]",userId);
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId) {
        String password = passwordEncoder.encode("123456");
        Logger.info(LoggerServer.SPRING_SECURITY,"密码:[{}]",password);
        return new SocialUser(userId,password,true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
