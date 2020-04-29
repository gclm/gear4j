package club.gclmit.chaos.core.lang.logger;

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
    FASTJSON("FastJSON"),

    SWAGGER("Swagger"),

    WEBSOCKET("WebSocket"),
    OKHTTP("OkHttp"),
    HTTPCLIENT("Httpclient"),

    // ~ Chaos 组件
    // ===================================================================================================

    CHAOS("Chaos"),
    CHAOS_SECURITY("Chaos Security"),
    GPI("GPI"),
    CATKIN("Catkin"),

    // ~ MVC 架构
    // ===================================================================================================

    CONTROLLER("Controller"),
    SERVICE("Service"),
    MAPPER("Mapper"),
    UTILS("Utils"),
    HELPER("Helper"),
    INTERCEPTOR("Interceptor"),
    FILTER("Filter"),
    ASPECT("Aspect"),

    // model
    POJO("pojo"),
    ENTITY("entity"),
    DTO("DTO"),
    VO("VO"),
    PARAM("Param"),
    QUERY("Query");

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
