package club.gclmit.chaos.starter.config;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
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

    private static int serverPort;

    public static int getPort() {
        return serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
        Logger.info("Get WebServer port {}", serverPort);
    }
}
//@Component
//public class SpringServiceInfo {
//
//    @Autowired
//    private Environment environment;
//
//    /**
//     * 服务端口号
//     */
//    private static String serverPort;
//
//    public SpringServiceInfo() {
//
//        serverPort = environment.getProperty("local.server.port");
//
//    }
//
//    /**
//     *  获取服务启动端口
//     *
//     * @author gclm
//     * @date 2020/5/9 10:36 上午
//     * @return: java.lang.Integer
//     */
//    public static Integer getPort(){
//        return StringUtils.isBlank(serverPort) ? 8080 : Integer.parseInt(serverPort);
//    }
//}
