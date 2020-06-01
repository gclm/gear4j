package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.lang.Logger;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Spring Service 服务工具类
 * </p>
 *
 * @author gclm
 */
@Component
public class SpringServiceHandler implements ApplicationListener<WebServerInitializedEvent> {

    /**
     * 服务端口号
     */
    private static int serverPort;

    /**
     * <p>
     *  获取项目服务端口
     * </p>
     *
     * @author gclm
     * @param event WebServerInitializedEvent
     */
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
        Logger.info("Get WebServer port {} WebServer Doc http://localhost:{}/doc.html", serverPort,serverPort);
    }

    public static int getPort() {
        return serverPort;
    }
}