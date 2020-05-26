package club.gclmit.chaos.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * <p>
 * chaos web 配置
 * </p>
 *
 * @author gclm
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
