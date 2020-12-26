package club.gclmit.chaos.waf.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *  waf 配置
 * </p>
 *
 * @author gclm
 */
@Getter
@Setter
public class ChaosWafProperties {

    /**
     * xss 配置
     */
    private XssProperties xss;
}
