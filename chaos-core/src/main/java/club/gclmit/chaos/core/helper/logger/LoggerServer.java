package club.gclmit.chaos.core.helper.logger;

/**
 * <p>
 * log 常见服务
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/3 2:10 下午
 * @version: V1.0
 * @since 1.8
 */
public enum LoggerServer {

    /**
     * 组件日志
     */
    // ~ 框架日志
    // ===================================================================================================

    /**
     *   Spring 全家桶
     */
    SPRING_BOOT("Spring Boot"),
    SPRING_SECURITY("Spring Security"),
    SPRING_JPA("Spring Data JPA"),
    SPRING_SESSION("Spring Session"),
    SPRING_SOCIAL("Spring Social"),
    SPRING_SHELL("Spring Shell"),


    // ~ 其他组件
    // ===================================================================================================

    MYBATIS("Mybatis"),
    DBUTILS("DBUtils"),
    DRUID("Druid"),
    JACKJSON("JackJson"),
    SWAGGER("Swagger"),
    FASTJSON("FastJSON"),
    WEBSOCKET("WebSocket"),
    OKHTTP("OkHttp"),
    HTTPCLIENT("httpclient"),


    // ~ Chaos 组件
    // ===================================================================================================

    CHAOS_DB("Chaos DB"),
    CHAOS_STORAGE("Chaos Storage"),
    CHAOS_SECURITY("Chaos Security"),
    CHAOS_LOGGER("Chaos Logger"),

    // ~ MVC 架构
    // ===================================================================================================

    CONTROLLER("Controller 层"),
    SERVICE("Service 层"),
    MAPPER("Mapper 层"),
    DAO("Dao 层"),

    // ~ 文件管理相关业务
    // ===================================================================================================

    File("File"),
    OSS("OSS");

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private LoggerServer(String key) {
        this.key = key;
    }
}
