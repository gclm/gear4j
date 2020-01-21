package club.gclmit.chaos.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * <p>
 *   SpringBoot demo 启动类
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/22 14:29
 * @version: V1.0
 * @since 1.8
 */
@SpringBootApplication(exclude = {
//       SecurityAutoConfiguration.class,
       RedisAutoConfiguration.class
})
public class ChaosSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChaosSecurityApplication.class, args);
    }
}
