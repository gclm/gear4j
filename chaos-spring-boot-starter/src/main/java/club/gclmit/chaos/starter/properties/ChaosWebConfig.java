package club.gclmit.chaos.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * <p>
 * chaos web 配置
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/30 2:26 上午
 * @version: V1.0
 * @since 1.8
 */
@ConfigurationProperties("chaos.web")
public class ChaosWebConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  xss 过滤器
     */
    private boolean enableXss = false;


    public boolean getEnableXss() {
        return enableXss;
    }

    public void setEnableXss(boolean enableXss) {
        this.enableXss = enableXss;
    }
}
