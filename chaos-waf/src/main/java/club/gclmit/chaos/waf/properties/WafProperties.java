package club.gclmit.chaos.waf.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Chaos Web配置类
 *
 * @author gclm
 */
@Getter
@Setter
@ConfigurationProperties("chaos.waf")
public class WafProperties {

    private XssProperties xss;
}
