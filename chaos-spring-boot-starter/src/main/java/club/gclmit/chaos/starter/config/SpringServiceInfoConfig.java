package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.lang.Logger;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Spring Service 服务信息工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/9 10:28 上午
 * @version: V1.0
 * @since 1.8
 */
@Component
public class SpringServiceInfoConfig implements ApplicationListener<WebServerInitializedEvent> {

    /**
     * 服务端口号
     */
    private static int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
        Logger.info("Get WebServer port {} WebServer Doc http://localhost:{}/doc.html", serverPort,serverPort);
    }

    public static int getPort() {
        return serverPort;
    }
}